package pl.visa.labmanager;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LabManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabManagerApplication.class, args);
    }
    public static Dotenv dotenv = Dotenv.configure().load();
}
