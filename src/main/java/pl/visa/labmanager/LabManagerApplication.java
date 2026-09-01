package pl.visa.labmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LabManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabManagerApplication.class, args);
    }

    public static String photosPath = "src/main/resources/static/images";

}
