package com.jonasdrechsel.kilterboardleaderboard.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.login.endpoint}")
    private String loginEndpoint;

    @Value("${external.api.username}")
    private String username;

    @Value("${external.api.password}")
    private String password;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(apiUrl).build();
    }

    @Bean
    public String apiKey(WebClient webClient) {
        return webClient.post()
                .uri(loginEndpoint)
                .bodyValue(new LoginRequest(username, password))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    static class LoginRequest {
        private String username;
        private String password;
        // required by kilter board api
        private String pp;
        private String tou;
        private String ua;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
            pp = "accepted";
            tou = "accepted";
            ua = "app";
        }
    }
}