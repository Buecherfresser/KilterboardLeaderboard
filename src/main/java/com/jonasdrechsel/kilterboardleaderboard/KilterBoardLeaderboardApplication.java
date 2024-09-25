package com.jonasdrechsel.kilterboardleaderboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KilterBoardLeaderboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(KilterBoardLeaderboardApplication.class, args);
    }

}
