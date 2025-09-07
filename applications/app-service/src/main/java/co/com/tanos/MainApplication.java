package co.com.tanos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

@SpringBootApplication/*(exclude = {
        org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration.class
})*/
@ConfigurationPropertiesScan("co.com.tanos.r2dbc.config")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);


    }
}
