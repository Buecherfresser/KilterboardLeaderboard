package com.jonasdrechsel.kilterboardleaderboard;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
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


    public KilterUser[] searchUser(String name) {

        Mono<String> userMono = webClientBuilder.build()
                .get()
                .uri("https://kilterboardapp.com/explore?q=" + name + "&t=user")
                .header("Accept", "application/json, text/plain, */*")
                .header("Cookie", "token=" + apiToken)
                .retrieve()
                .bodyToMono(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION, true);
        KilterUser[] kilterUsers = null;
        try {
            String userJson = userMono.block();
            JsonNode jsonNode = objectMapper.readTree(userJson);
            String usersJson = jsonNode.get("results").toString();
            kilterUsers = objectMapper.readValue(usersJson, KilterUser[].class);
        } catch (Exception e) {
            System.err.println(e.getCause() + "\n" + e.getMessage());
        }
        return kilterUsers;
    }


    public Mono<String> callExternalApi2() {
        return webClientBuilder.build()
                .get()
                .uri("https://api.external-service2.com/data")
                .retrieve()
                .bodyToMono(String.class);
    }
}