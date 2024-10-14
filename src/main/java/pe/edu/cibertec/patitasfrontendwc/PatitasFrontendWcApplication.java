package pe.edu.cibertec.patitasfrontendwc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class PatitasFrontendWcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatitasFrontendWcApplication.class, args);
    }

}
