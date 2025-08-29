FROM eclipse-temurin:17.0.5_8-jre-focal
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8088 8089
ENTRYPOINT ["java", "-Dserver.port=8088", "-Dmanagement.server.port=8089", "-Djavax.net.ssl.keyStore=keystore.p12",  "-jar", "app.jar"]