cd microservices

spring init \
--boot-version=3.4.4 \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=account-address-service \
--package-name=nl.optiperf.microservices.core.address \
--groupId=nl.optiperf.microservices.core.address \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
account-address-service
cd ..