package com.jonasdrechsel.kilterboardleaderboard.Database;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import com.jonasdrechsel.kilterboardleaderboard.KilterExternalApiService;
import com.jonasdrechsel.kilterboardleaderboard.LeaderboardController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class LeaderboardService {
    private final ClimbService climbService;
    private final UserService userService;
    private final KilterExternalApiService kilterApi;

    @Autowired
    public LeaderboardService(ClimbService climbService, UserService userService, KilterExternalApiService kilterExternalApiService) {
        this.climbService = climbService;
        this.userService = userService;
        this.kilterApi = kilterExternalApiService;
    }
    public List<KilterUser> refreshLeaderboard() {
        List<KilterUser> users = userService.getAll();
        for (KilterUser user : users) {
            updateClimbs(user);
        }
        return userService.getOrderedByPp();
    }

    public Climb[] updateClimbs(KilterUser user) {
        long userId = user.getId();
        Climb[] climbs;
        try {
            climbs = kilterApi.getClimbs(user.getId());
        }
        catch (Error e) {
            throw new Error("User does not have any climbs yet.");
        }
        if (climbs == null) {
            user.setPp(0);
            userService.saveUser(user);
            return new Climb[0];
        }
        Arrays.parallelSetAll(climbs, i -> climbService.addNameToClimb(climbs[i]));
        Arrays.sort(climbs, new Comparator<Climb>() {
            @Override
            public int compare(Climb o1, Climb o2) {
                return Integer.compare(o1.getDifficulty(), o2.getDifficulty()) * -1;
            }
        });
        int totalPp = 0;
        // Field 1: pure PP, Field 2: weighted PP
        int[] pp = new int[2];
        int ascents = 0;
        int flashes = 0;
        for (Climb c : climbs) {
            pp = climbService.calculatePp(c.getDifficulty(), 0);
            c.setPp(pp[0]);
            climbService.saveClimb(c);
            if (c.getType().equals("ascent")) {
                pp = climbService.calculatePp(c.getDifficulty(), ascents);
                totalPp += pp[1];
                ascents++;
                if (c.getBid_count() == 1) {
                    flashes++;
                }
            }

        }
        user.setPp(totalPp);
        user.setAscents(ascents);
        user.setFlashes(flashes);
        userService.saveUser(user);
//        userService.updatePp(user, totalPp);
        return climbs;
    }
}
