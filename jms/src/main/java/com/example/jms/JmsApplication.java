package com.example.jms;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsClient;

@SpringBootApplication
public class JmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmsApplication.class, args);
    }

}

@Configuration
class JmsConfiguration {

    static final String Q = "pjug";

    @JmsListener(destination = Q)
    void on(String message) {
        System.out.println("Received: " + message);
    }

    @Bean
    ApplicationRunner runner(JmsClient jmsClient) {
        return args -> jmsClient
                .destination(Q)
                .send("Hello World!");
    }
}