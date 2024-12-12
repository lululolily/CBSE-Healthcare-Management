package org.cbse.project.healthcare_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HealthcareManagementApplication {

	@GetMapping("/main")
    public String check() {
        return "Hello World There";
    }

	public static void main(String[] args) {
		SpringApplication.run(HealthcareManagementApplication.class, args);
	}

}
