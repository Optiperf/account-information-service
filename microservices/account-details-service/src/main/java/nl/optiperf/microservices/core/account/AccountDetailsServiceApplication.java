package nl.optiperf.microservices.core.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "nl.optiperf.microservices.core.account")
public class AccountDetailsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountDetailsServiceApplication.class, args);
		System.out.println("Account Details Service is up and running!");
	}

}
