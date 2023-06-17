package pl.edu.pb.wi;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.pb.wi.config.DatabaseConfig;

@SpringBootApplication
@AllArgsConstructor
public class JavaBusinessApplication implements CommandLineRunner {
    DatabaseConfig databaseConfig;

    public static void main(String[] args) {
        SpringApplication.run(JavaBusinessApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        databaseConfig.init();
    }
}
