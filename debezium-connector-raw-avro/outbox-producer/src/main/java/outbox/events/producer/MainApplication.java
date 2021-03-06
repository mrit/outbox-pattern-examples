package outbox.events.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories
@EnableScheduling
@EnableAutoConfiguration
@SpringBootApplication
public class MainApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
