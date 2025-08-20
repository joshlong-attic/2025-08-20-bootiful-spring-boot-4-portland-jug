package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

@Controller
@ResponseBody
class CoraIberkleidRestController {

    private final RestClient http;

    CoraIberkleidRestController(RestClient.Builder http) {
        this.http = http.build();
    }

    private final Object monitor = new Object();


    @GetMapping("/delay")
    String delay() {
        var response = this.http
                .get()
                .uri("http://localhost:80/delay/5")
                .retrieve()
                .body(String.class);
        synchronized (this.monitor) {
            var msg = Thread.currentThread() + ":";

            msg += Thread.currentThread();
            System.out.println(msg);
            return response;
        }
    }

}