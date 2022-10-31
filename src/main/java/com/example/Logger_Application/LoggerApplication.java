package com.example.Logger_Application;

import com.example.Logger_Application.Model.Client;
import com.example.Logger_Application.Model.ClientRole;
import com.example.Logger_Application.Services.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import java.util.UUID;
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class LoggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoggerApplication.class, args);
	}
	@Bean
	CommandLineRunner Start(ClientService clientService) {
		return args -> {
			Client client = new Client(1, "Admin", "admin123@gmail.com", "123passworD.", ClientRole.ADMIN, UUID.randomUUID().toString());
			clientService.save(client);
			clientService.setRoleToAdmin(client);
		};
	}
}