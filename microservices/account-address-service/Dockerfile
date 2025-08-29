FROM eclipse-temurin:17.0.5_8-jre-focal
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8084 8085
ENTRYPOINT ["java", "-Dserver.port=8084", "-Dmanagement.server.port=8085", "-Djavax.net.ssl.keyStore=keystore.p12",  "-jar", "app.jar"]