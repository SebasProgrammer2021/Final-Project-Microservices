package co.edu.uniquindio.autenticacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoginApp {
	public static void main(String[] args) {
		SpringApplication.run(LoginApp.class, args);
	}

}
