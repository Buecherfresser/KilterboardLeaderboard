package com.jonasdrechsel.kilterboardleaderboard;

import com.jonasdrechsel.kilterboardleaderboard.Database.ClimbService;
import com.jonasdrechsel.kilterboardleaderboard.Database.LeaderboardService;
import com.jonasdrechsel.kilterboardleaderboard.Database.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduleTasks {
    private final LeaderboardService leaderboardService;
    @Autowired
    public ScheduleTasks (LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @Scheduled(fixedDelay = 60000)
    public void refreshLeaderboard() {
        System.out.println("Refreshing Leaderboard.");
        leaderboardService.refreshLeaderboard();
    }
}