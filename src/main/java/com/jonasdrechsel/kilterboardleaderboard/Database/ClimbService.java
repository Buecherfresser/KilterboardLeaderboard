package com.jonasdrechsel.kilterboardleaderboard.Database;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import com.jonasdrechsel.kilterboardleaderboard.KilterExternalApiService;
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
    public Climb saveUser(Climb climb) {
        return climbRepository.save(climb);
    }
    public void deleteUser(String id) {
        climbRepository.deleteById(id);
    }

    public List<Climb> getAll() {
        return climbRepository.findAll();
    }
    public Climb[] updateClimbs(KilterUser user) {
        long userId = user.getId();
        Climb[] climbs;
        try {
            climbs = kilterApi.getClimbs(user.getId());
            Arrays.parallelSetAll(climbs, i -> addNameToClimb(climbs[i]));
        }
        catch (Error e) {
            throw new Error("User does not have any climbs yet.");
        }

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
        double purePP = 0.1 * Math.pow(difficulty, 2.0);
        return (int) (Math.round((purePP * Math.pow(0.9, n))));
    }

//    public KilterUser[] searchUser(String name) {
//        return kilterApi.searchUser(name);
//    }

    public List<Climb> getClimbs(int id) {
        return climbRespository.findByUserIdOrderByPpDesc(id);
    }
    private String extractH1Content(String html) {
        Document document = Jsoup.parse(html);
        return document.select("h1").text();
    }
    private Climb addNameToClimb(Climb climb) {
        String response = kilterApi.getClimbName(climb.getClimbUuid()).block();
        String name = extractH1Content(response);
        climb.setName(name);
        return climb;
    }
}
