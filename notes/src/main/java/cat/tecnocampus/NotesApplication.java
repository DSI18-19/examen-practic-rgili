package cat.tecnocampus;

import cat.tecnocampus.persistence.NoteLabDAO;
import cat.tecnocampus.useCases.NotesUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@EnableCircuitBreaker
@EnableScheduling
@SpringBootApplication
public class NotesApplication {

	@Autowired
	private NoteLabDAO noteLabDAO;

	@Autowired
	private NotesUseCases notesUseCases;

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}
}
