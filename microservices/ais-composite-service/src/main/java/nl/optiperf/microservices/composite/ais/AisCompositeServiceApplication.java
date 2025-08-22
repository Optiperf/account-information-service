package nl.optiperf.microservices.composite.ais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("nl.optiperf.microservices")
public class AisCompositeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AisCompositeServiceApplication.class, args);
	}

}
