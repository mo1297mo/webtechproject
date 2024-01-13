package webtech.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import webtech.backend.seed.DatabaseSeeder;

@SpringBootApplication
@RestController
public class BackendApplication {

	@Autowired
	private DatabaseSeeder seeder;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@PostConstruct
	public void init() {
		seeder.updateTablesAndAvailability();
	}

}
