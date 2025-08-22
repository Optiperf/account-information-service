cd microservices

spring init \
--boot-version=3.4.4 \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=ais-composite-service \
--package-name=nl.optiperf.microservices.composite.ais \
--groupId=nl.optiperf.microservices.composite.ais \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
ais-composite-service
cd ..