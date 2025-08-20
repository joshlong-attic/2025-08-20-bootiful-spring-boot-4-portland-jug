package com.example.versions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@SpringBootApplication
public class VersionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VersionsApplication.class, args);
    }


}

@Configuration
class VersionConfiguration implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
                .setVersionRequired(false)
                .setDefaultVersion("1.0")
                .useRequestHeader("X-API-Version");
    }
}


@Controller
@ResponseBody
class GreetingsController {

    static final String MSG = "hello, world!";

    @GetMapping(value = "/hello" ,version = "2.0")
    Map<String, String> hello() {
        return Map.of("message", MSG);
    }

    @GetMapping(value = "/hello" ,version = "1.0")
    String helloV1() {
        return MSG;
    }
}