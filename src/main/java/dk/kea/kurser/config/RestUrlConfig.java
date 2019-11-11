package dk.kea.kurser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestUrlConfig {

    @Bean
    public String restUrl() {
        return "http://35.159.46.191";
    }
}
