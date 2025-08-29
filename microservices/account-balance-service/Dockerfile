FROM eclipse-temurin:17.0.5_8-jre-focal
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8086 8087
ENTRYPOINT ["java", "-Dserver.port=8086", "-Dmanagement.server.port=8087", "-Djavax.net.ssl.keyStore=keystore.p12",  "-jar", "app.jar"]