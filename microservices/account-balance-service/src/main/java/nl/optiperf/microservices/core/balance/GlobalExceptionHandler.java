package nl.optiperf.microservices.core.balance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.support.WebExchangeBindException; // For WebFlux validation
import org.springframework.web.server.ServerWebInputException;     // For WebFlux input errors (e.g., malformed JSON)
import jakarta.validation.ConstraintViolationException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handler for JSR 380 Bean Validation errors with @Valid in WebFlux
    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleValidationException(WebExchangeBindException ex) {
        log.warn("Validation error (WebExchangeBindException) occurred. Reason: {}", ex.getReason(), ex);

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        org.springframework.validation.FieldError::getField,
                                          fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid value",
                                          (msg1, msg2) -> msg1 + "; " + msg2)); // Handle duplicate fields if any

        String globalErrors = ex.getBindingResult().getGlobalErrors().stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.joining("; "));

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Validation failed");
        if (!fieldErrors.isEmpty()) {
            responseBody.put("errors", fieldErrors);
        }
        if (globalErrors != null && !globalErrors.trim().isEmpty()) {
            responseBody.put("globalErrors", globalErrors);
        }
        // If no specific errors found, provide the general reason from the exception
        if (fieldErrors.isEmpty() && (globalErrors == null || globalErrors.trim().isEmpty())) {
             responseBody.put("detail", "Request is invalid. Reason: " + ex.getReason());
        }
        
        log.debug("Responding with structured validation errors (WebExchangeBindException): {}", responseBody);
        return responseBody;
    }

    // Handler for malformed JSON or other input decoding issues in WebFlux
    @ExceptionHandler(ServerWebInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleServerWebInputException(ServerWebInputException ex) {
        log.warn("Request input error (ServerWebInputException) occurred. Status: {}, Reason: {}", ex.getStatusCode(), ex.getReason(), ex);
        Map<String, Object> responseBody = new HashMap<>();

        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String fieldName = ife.getPath().stream()
                                .map(JsonMappingException.Reference::getFieldName)
                                .filter(name -> name != null) // Filter out null field names (e.g. for list indices)
                                .collect(Collectors.joining("."));

            responseBody.put("message", "Invalid value for field");
            if (!fieldName.isEmpty()) {
                responseBody.put("message", "Invalid value for field '" + fieldName + "'");
                responseBody.put("field", fieldName);
            }
            responseBody.put("invalidValue", ife.getValue());
            if (ife.getTargetType() != null && ife.getTargetType().isEnum()) {
                Object[] enumValues = ife.getTargetType().getEnumConstants();
                responseBody.put("acceptedValues", Arrays.stream(enumValues)
                                                        .map(Object::toString)
                                                        .collect(Collectors.toList()));
                responseBody.put("detail", "Value '" + ife.getValue() + "' is not one of the accepted values: " + Arrays.toString(enumValues));
            } else {
                responseBody.put("detail", "Invalid format for field '" + fieldName + "'. Expected type: " + (ife.getTargetType() != null ? ife.getTargetType().getSimpleName() : "unknown"));
            }
        } else if (cause instanceof DecodingException) {
            responseBody.put("message", "Request input error");
            responseBody.put("detail", ex.getReason() != null ? ex.getReason() : "Invalid request payload. JSON decoding error.");
            responseBody.put("cause", "JSON decoding error: " + cause.getMessage());
        } else {
            responseBody.put("message", "Request input error");
            responseBody.put("detail", ex.getReason() != null ? ex.getReason() : "Invalid request payload.");
        }
        log.debug("Responding with structured input error (ServerWebInputException): {}", responseBody);
        return responseBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleConstraintViolationException(ConstraintViolationException ex) {
    log.warn("Constraint violation error occurred: {}", ex.getMessage());
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("message", "Validation failed at persistence layer");
    Map<String, String> errors = ex.getConstraintViolations().stream()
            .collect(Collectors.toMap(
                    cv -> cv.getPropertyPath().toString(),
                    ConstraintViolation::getMessage,
                    (msg1, msg2) -> msg1 + "; " + msg2 // In case of multiple violations on the same path (less common)
            ));
    responseBody.put("errors", errors);
    return responseBody;
}

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        log.warn("Handling ResponseStatusException: Status Code: {}, Reason: '{}'", ex.getStatusCode(), ex.getReason(), ex);
        Map<String, Object> body = new HashMap<>();

        String generalMessage;
        HttpStatus status = (HttpStatus) ex.getStatusCode(); // Correctly cast HttpStatusCode to HttpStatus

        if (status == HttpStatus.NOT_FOUND) {
            generalMessage = "Resource Not Found";
        } else if (status == HttpStatus.CONFLICT) {
            generalMessage = "Conflict";
        } else if (status == HttpStatus.BAD_REQUEST) {
            generalMessage = "Bad Request";
        } else if (status.is4xxClientError()) {
            generalMessage = "Client Error";
        } else if (status.is5xxServerError()) {
            generalMessage = "Server Error";
        } else {
            generalMessage = "Operation Failed";
        }

        body.put("message", generalMessage);
        if (ex.getReason() != null) {
            body.put("detail", ex.getReason()); // This will include your custom message
        }
        body.put("status", status.value());
        // body.put("timestamp", java.time.LocalDateTime.now().toString()); // Optional: add timestamp

        return new ResponseEntity<>(body, status);
    }
}