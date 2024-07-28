package com.jonasdrechsel.kilterboardleaderboard.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

@PropertySource("classpath:application.properties")
@Configuration
public class ShutdownConfig {

    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.logout.endpoint}")
    private String logoutEndpoint;

    private final WebClient.Builder webClientBuilder;
    private final String apiKey;

    @Autowired
    public ShutdownConfig(WebClient.Builder webClientBuilder, String apiKey) {
        this.webClientBuilder = webClientBuilder;
        this.apiKey = apiKey;
    }

    @EventListener
    public void onShutdown(ContextClosedEvent event) {
        webClientBuilder.build().delete()
                .uri(apiUrl + logoutEndpoint + apiKey)
                .header("Cookie", "PHPSESSID=12345678")
                .header("Authorization", "Bearer " + apiKey)
                .retrieve()
//                .onStatus(HttpStatusCode.valueOf(200), response ->
//                {
//                    System.out.println(apiKey + " was successfully logged out.");
//
//                })
                .bodyToMono(Void.class)
                .block();
    }
}
