package com.jonasdrechsel.kilterboardleaderboard;

import com.jonasdrechsel.kilterboardleaderboard.Database.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ScheduleTasks {
    private final LeaderboardService leaderboardService;

    @Autowired
    public ScheduleTasks(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    // hourly
//    @Scheduled(fixedDelay = 3600000)
    public void refreshLeaderboard() {
        System.out.println("Refreshing Leaderboard.");
        leaderboardService.refreshLeaderboard();
    }

    // daily
//    @Scheduled(fixedDelay = 86400000)
    public void refreshLeaderboardCompletely() {
        System.out.println("Refreshing Leaderboard.");
        leaderboardService.refreshLeaderboardCompletely();
    }
}