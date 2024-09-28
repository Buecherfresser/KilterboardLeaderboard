package com.jonasdrechsel.kilterboardleaderboard.Database;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import com.jonasdrechsel.kilterboardleaderboard.KilterExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
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

    public void refreshLeaderboard() {
        List<KilterUser> users = userService.getAll();
        for (KilterUser user : users) {
            addNewClimbsAndReload(user);
        }
    }

    public void refreshLeaderboardCompletely() {
        List<KilterUser> users = userService.getAll();
        for (KilterUser user : users) {
            reloadAllClimbs(user);
        }
    }

    public void reloadAllClimbs(KilterUser user) {
        Climb[] climbsFromApi = kilterApi.getClimbs(user.getId());
        HashSet<String> newClimbs = new HashSet<>();
        for (Climb climb : climbsFromApi) {
            climb.setId(climb.getUuidKilter() + climb.getClimbUuidKilter());
            newClimbs.add(climb.getId());
        }
        reloadClimbs(user, climbsFromApi, newClimbs);
    }

    public void addNewClimbsAndReload(KilterUser user) {
        List<Climb> climbsInDatabase = climbService.getClimbs(user.getId());
        Climb[] climbsFromApi = kilterApi.getClimbs(user.getId());
        HashSet<String> newClimbs = new HashSet<>();
        // make a Hashset for id of climbsInDatabase
        HashSet<String> ids = new HashSet<>();
        for (Climb c : climbsInDatabase) {
            ids.add(c.getId());
        }
        for (Climb c : climbsFromApi) {
            if (c.getId() == null) {
                c.setId(c.getUuidKilter() + c.getClimbUuidKilter());
            }
            if (!ids.contains(c.getId())) {
//                System.out.println("Adding new climb with id '" + c.getUuidKilter() + "' to user " + user.getName());
                climbsInDatabase.add(c);
                newClimbs.add(c.getId());
            }
        }
        reloadClimbs(user, climbsInDatabase.toArray(new Climb[0]), newClimbs);
    }

    // TODO: replace climbs and newClimbsIds with oldClimbs and newClimbs to make this function more reusable
    public void reloadClimbs(KilterUser user, Climb[] climbs, HashSet<String> newClimbsIds) {
        DateTimeFormatter kilterFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        Climb[] climbs = kilterApi.getClimbs(user.getId());
//        if (climbs == null) {
//            user.setPpWeighted(0);
//            userService.saveUser(user);
//            return;
//        }
        Arrays.parallelSetAll(climbs, i -> climbService.addNameToClimb(climbs[i]));
        Arrays.sort(climbs, new Comparator<Climb>() {
            @Override
            public int compare(Climb o1, Climb o2) {
                if (o1.getDifficulty() != o2.getDifficulty()) {
                    return Integer.compare(o1.getDifficulty(), o2.getDifficulty()) * -1;
                }
                LocalDateTime o1Date = LocalDateTime.parse(o1.getDate(), kilterFormatter);
                LocalDateTime o2Date = LocalDateTime.parse(o2.getDate(), kilterFormatter);
                return o1Date.compareTo(o2Date);
            }
        });
        int weightedPP = 0;
        int unweightedPP = 0;
        int weightedPPSeason1 = 0;
        int unweightedPPSeason1 = 0;
        // Field 1: pure PP, Field 2: weighted PP
        int[] pp;
        int[] ppSeason1;
        int ascents = 0;
        int ascentsSeason1 = 0;
        int flashes = 0;
        int flashesSeason1 = 0;
        int highestDifficulty = 0;
        LocalDateTime season1Start = LocalDate.of(2024, 8, 26).atStartOfDay();
        LocalDateTime season1End = LocalDate.of(2024, 9, 26).atStartOfDay();
        boolean changeToClimb = false;
        for (Climb c : climbs) {
            pp = climbService.calculatePp(c.getDifficulty(), ascents);
            if (c.getPpUnweighted() != pp[0]) {
                c.setPpUnweighted(pp[0]);
                changeToClimb = true;
            }
            if (c.getType().equals("ascent")) {
                if (c.getPpWeighted() != pp[1]) {
                    c.setPpWeighted(pp[1]);
                    changeToClimb = true;
                }
                unweightedPP += pp[0];
                weightedPP += pp[1];
                try {
                    LocalDateTime climbDate = LocalDateTime.parse(c.getDate(), kilterFormatter);
                    if (climbDate.isAfter(season1Start) && climbDate.isBefore(season1End)) {
                        ppSeason1 = climbService.calculatePp(c.getDifficulty(), ascentsSeason1);
                        if (c.getPpUnweightedSeason1() != ppSeason1[0]) {
                            c.setPpUnweightedSeason1(ppSeason1[0]);
                            changeToClimb = true;
                        }
                        if (c.getPpWeightedSeason1() != ppSeason1[1]) {
                            c.setPpWeightedSeason1(ppSeason1[1]);
                            changeToClimb = true;
                        }
                        unweightedPPSeason1 += ppSeason1[0];
                        weightedPPSeason1 += ppSeason1[1];
                        ascentsSeason1++;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println(e.getMessage());
                }
                ascents++;
                if (c.getBidCount() == 1) {
                    flashes++;
                }
            }
            if (c.getDifficulty() > highestDifficulty) {
                highestDifficulty = c.getDifficulty();
            }
            // if PP was changed or climb is new
            if (changeToClimb || newClimbsIds.contains(c.getId())) {
                climbService.saveClimb(c);
            }
            changeToClimb = false;
        }
        user.setAscents(ascents);
        user.setAscentsSeason1(ascentsSeason1);
        user.setFlashes(flashes);
        user.setFlashesSeason1(flashesSeason1);
        user.setHighestDifficulty(highestDifficulty);
        user.setPpWeighted(weightedPP);
        user.setPpUnweighted(unweightedPP);
        user.setPpUnweightedSeason1(unweightedPPSeason1);
        user.setPpWeightedSeason1(weightedPPSeason1);
        userService.saveUser(user);
    }

    public List<KilterUser> getLeaderboard() {
        return userService.getOrderedByPp();
    }

    public KilterUser addUser(KilterUser kilterUser) {
        reloadAllClimbs(kilterUser);
        return kilterUser;
    }
}
