package pub.pubbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// SpringBootApplication fires up a servlet container and serve up our service.
@SpringBootApplication
public class PubBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubBackendApplication.class, args);
	}

}
