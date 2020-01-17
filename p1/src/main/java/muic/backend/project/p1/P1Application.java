package muic.backend.project.p1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class P1Application {

	public static void main(String[] args) {
		SpringApplication.run(P1Application.class, args);
	}

}
