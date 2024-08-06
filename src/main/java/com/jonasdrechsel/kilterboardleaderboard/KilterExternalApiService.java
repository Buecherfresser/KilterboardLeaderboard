package com.jonasdrechsel.kilterboardleaderboard;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KilterExternalApiService {
    private final String apiToken;
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;


    @Autowired
    public KilterExternalApiService(String apiToken, WebClient.Builder webClientBuilder) {
        this.apiToken = apiToken;
        this.webClientBuilder = webClientBuilder;
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION, true);
    }


    public KilterUser[] searchUser(String name) {

        Mono<String> userMono = webClientBuilder.build()
                .get()
                .uri("https://kilterboardapp.com/explore?q=" + name + "&t=user")
                .header("Accept", "application/json, text/plain, */*")
                .header("Cookie", "token=" + apiToken)
                .retrieve()
                .bodyToMono(String.class);


        KilterUser[] kilterUsers = null;
        try {
            String usersJson = userMono.block();
            JsonNode jsonNode = objectMapper.readTree(usersJson);
            usersJson = jsonNode.get("results").toString();
            kilterUsers = objectMapper.readValue(usersJson, KilterUser[].class);
        } catch (Exception e) {
            System.err.println(e.getCause() + "\n" + e.getMessage());
        }
        return kilterUsers;
    }


    public Climb[] getClimbs(long userId) {
        Mono<String> ascentsMono = webClientBuilder.build()
                .get()
                .uri("https://kilterboardapp.com/users/" + userId +"/logbook?types=bid,ascent")
                .header("Accept", "application/json, text/plain, */*")
                .header("Cookie", "token=" + apiToken)
                .retrieve()
                .bodyToMono(String.class);
        Climb[] climbs = null;
        try {
            String ascentsJson = ascentsMono.block();
            JsonNode jsonNode = objectMapper.readTree(ascentsJson);
            ascentsJson = jsonNode.get("logbook").toString();
            climbs = objectMapper.readValue(ascentsJson, Climb[].class);
        } catch (Exception e) {
            System.err.println(e.getCause() + "\n" + e.getMessage());
        }
        return climbs;
    }
}