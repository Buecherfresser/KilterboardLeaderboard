package com.jonasdrechsel.kilterboardleaderboard.Configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;
@PropertySource("classpath:application.properties")
@PropertySource("classpath:credentials.properties")
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
    public String apiKey(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) throws Exception {
        String token = "";
        try {
        String response = webClientBuilder.build().post()
                .uri(apiUrl + loginEndpoint)
                .header("Content-Type", "Application/json")
                // TODO check wether php session id works
                .header("Cookie", "PHPSESSID=12345678")
                // TODO: check wether all params are being sent
                .bodyValue(new LoginRequest(username, password))
                .retrieve()
                .bodyToMono(String.class)
                .block();
            JsonNode jsonNode = objectMapper.readTree(response);
            token = jsonNode.get("session").get("token").asText();
            String userId = jsonNode.get("session").get("user_id").asText();

            System.out.println("Token: " + token);
            System.out.println("User ID: " + userId);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (token.isBlank()) {
            throw new Exception("Couldn't get Api Token");
        }
        return token;
    }

    static class LoginRequest {
        private String password;
        private String username;
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

        public String getPassword() {
            return password;
        }

        public String getPp() {
            return pp;
        }

        public String getTou() {
            return tou;
        }

        public String getUa() {
            return ua;
        }

        public String getUsername() {
            return username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPp(String pp) {
            this.pp = pp;
        }

        public void setTou(String tou) {
            this.tou = tou;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}