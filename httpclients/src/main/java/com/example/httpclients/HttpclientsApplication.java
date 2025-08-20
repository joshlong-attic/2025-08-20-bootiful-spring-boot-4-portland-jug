package com.example.httpclients;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.registry.ImportHttpServices;

@ImportHttpServices(value = {CatFactNinjaClient.class})
@SpringBootApplication
public class HttpclientsApplication {


    public static void main(String[] args) {
        SpringApplication.run(HttpclientsApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(CatFactNinjaClient client) {
        return _ -> System.out.println(client.fact());
    }

    /*
    @Bean
    CatFactNinjaClient catFactNinjaClient (RestClient.Builder builder) {
        var rcea = RestClientAdapter.create(builder.build());
        var build = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(rcea)
                .build();
        return build
                .createClient(CatFactNinjaClient.class);
    }
     */

}

interface CatFactNinjaClient {

    @GetExchange("https://catfact.ninja/fact")
    CatFact fact();


}

record CatFact(String fact, int length) {
}

/*
@Component
class CatFactNinjaClient {

    private final RestClient http;

    CatFactNinjaClient(RestClient.Builder http) {
        this.http = http.build();
    }

    CatFact fact() {
        return this.http
                .get()
                .uri("https://catfact.ninja/fact")
                .retrieve()
                .body(CatFact.class);
    }
}

 */