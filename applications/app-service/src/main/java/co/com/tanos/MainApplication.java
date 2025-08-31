package co.com.tanos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("co.com.tanos.r2dbc.config")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);


    }
}
