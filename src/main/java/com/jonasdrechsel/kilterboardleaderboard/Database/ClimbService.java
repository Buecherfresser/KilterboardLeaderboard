package com.jonasdrechsel.kilterboardleaderboard.Database;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import com.jonasdrechsel.kilterboardleaderboard.KilterExternalApiService;
import jakarta.transaction.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class ClimbService {
    private final ClimbRespository climbRepository;
    private final KilterExternalApiService kilterApi;
    private final UserService userService;
    private final ClimbRespository climbRespository;

    @Autowired
    public ClimbService(ClimbRespository climbRepository, KilterExternalApiService kilterApi, UserService userService, ClimbRespository climbRespository){
        this.climbRepository = climbRepository;
        this.kilterApi = kilterApi;
        this.userService = userService;
        this.climbRespository = climbRespository;
    }
    public Climb saveClimb(Climb climb) {
        return climbRepository.save(climb);
    }
//    public void deleteUser(String id) {
//        climbRepository.deleteById(id);
//    }

    public List<Climb> getAll() {
        return climbRepository.findAll();
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
        Arrays.parallelSetAll(climbs, i -> addNameToClimb(climbs[i]));
        Arrays.sort(climbs, new Comparator<Climb>() {
            @Override
            public int compare(Climb o1, Climb o2) {
                return Integer.compare(o1.getDifficulty(), o2.getDifficulty()) * -1;
            }
        });
        int counter = 0;
        int totalPp = 0;
        int pp = 0;
        for (Climb c : climbs) {
            pp = calculatePp(c.getDifficulty(), counter);
            totalPp += pp;
            c.setPp(pp);
            counter++;
            climbRepository.save(c);
        }
        user.setPp(totalPp);
        userService.saveUser(user);
//        userService.updatePp(user, totalPp);
        return climbs;
    }

    private int calculatePp(int difficulty, int n) {
        double purePP = 0;
        if (difficulty > 10) {
            purePP = 0.2 * Math.pow(difficulty - 10, 2.0);
        }
        return (int) (Math.round((purePP * Math.pow(0.9, n))));
    }

    public List<Climb> getClimbs(long id) {
        return climbRespository.findClimbsByUserIdOrderByPpDesc(id);
    }
    private String extractH1Content(String html) {
        Document document = Jsoup.parse(html);
        return document.select("h1").text();
    }
    private Climb addNameToClimb(Climb climb) {
        String name = "";
        List<Climb> climbsDatabase = climbRepository.findClimbsByClimbUuid(climb.getClimbUuid());
        for (Climb c : climbsDatabase) {
            if (!c.getName().isBlank()) {
                name = c.getName();
            }
        }
        if (name.isBlank()) {
            String response = kilterApi.getClimbName(climb.getClimbUuid()).block();
            name = extractH1Content(response);
        }
        climb.setName(name);
        return climb;
    }
    @Transactional
    public void removeClimbs(long id) {
        climbRepository.removeClimbsByUserId(id);
    }
}
