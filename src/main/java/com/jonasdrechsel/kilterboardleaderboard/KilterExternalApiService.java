package com.jonasdrechsel.kilterboardleaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KilterExternalApiService {
    private final String apiToken;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public KilterExternalApiService(String apiToken, WebClient.Builder webClientBuilder) {
        this.apiToken = apiToken;
        this.webClientBuilder = webClientBuilder;
    }


    public Mono<String> searchUser(String name) {
        return webClientBuilder.build()
                .get()
                .uri("https://kilterboardapp.com/explore?q=" + name + "&t=user")
                .header("Accept", "application/json, text/plain, */*")
                .header("Cookie", "token=" + apiToken)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> callExternalApi2() {
        return webClientBuilder.build()
                .get()
                .uri("https://api.external-service2.com/data")
                .retrieve()
                .bodyToMono(String.class);
    }
}