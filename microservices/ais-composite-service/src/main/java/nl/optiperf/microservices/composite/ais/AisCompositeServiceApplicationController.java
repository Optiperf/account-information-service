package nl.optiperf.microservices.composite.ais;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ais-composite")
public class AisCompositeServiceApplicationController {
@GetMapping("/")
public String home() {
    return "Welcome to the Account Information Composite Service";
}   
}
