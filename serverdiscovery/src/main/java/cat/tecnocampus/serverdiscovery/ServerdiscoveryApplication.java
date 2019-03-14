package cat.tecnocampus.serverdiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServerdiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerdiscoveryApplication.class, args);
	}

}

