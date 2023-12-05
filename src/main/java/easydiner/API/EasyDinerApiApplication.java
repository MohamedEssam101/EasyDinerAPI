package easydiner.API;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Log4j2
public class EasyDinerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyDinerApiApplication.class, args);
	}
}
