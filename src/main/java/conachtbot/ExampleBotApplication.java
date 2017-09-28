package conachtbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExampleBotApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ExampleBotApplication.class, args);
    }

}
