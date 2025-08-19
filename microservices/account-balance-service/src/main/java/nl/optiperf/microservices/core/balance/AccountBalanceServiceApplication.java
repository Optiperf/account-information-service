package nl.optiperf.microservices.core.balance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountBalanceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountBalanceServiceApplication.class, args);
		System.out.println("Account Balance Service is up and running!");
	}

}
