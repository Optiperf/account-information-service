package nl.optiperf.microservices.core.address;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountAddressServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountAddressServiceApplication.class, args);
		System.out.println("Account Address Service is up and running!");
	}
}
