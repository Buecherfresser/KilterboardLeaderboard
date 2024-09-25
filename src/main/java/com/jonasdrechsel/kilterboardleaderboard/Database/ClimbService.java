package com.jonasdrechsel.kilterboardleaderboard.Database;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import com.jonasdrechsel.kilterboardleaderboard.KilterExternalApiService;
import jakarta.transaction.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClimbService {
    private final ClimbRepository climbRepository;
    private final KilterExternalApiService kilterApi;

    @Autowired
    public ClimbService(ClimbRepository climbRepository, KilterExternalApiService kilterApi) {
        this.climbRepository = climbRepository;
        this.kilterApi = kilterApi;
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

    // Field 1: pure PP, Field 2: weighted PP
    public int[] calculatePp(int difficulty, int n) {
        double purePP = 0;
        if (difficulty > 10) {
            purePP = 0.2 * Math.pow(difficulty - 10, 2.0);
        }
        return new int[]{(int) purePP, (int) (Math.round((purePP * Math.pow(0.9, n))))};
    }

    public List<Climb> getClimbs(long id) {
        Optional<List<Climb>> optionalClimbList = climbRepository.findClimbsByUserIdOrderByPpDesc(id);
        return optionalClimbList.orElseGet(ArrayList::new);
    }

    private String extractH1Content(String html) {
        Document document = Jsoup.parse(html);
        return document.select("h1").text();
    }

    public Climb addNameToClimb(Climb climb) {
        String name = "";
        Optional<List<Climb>> optionalClimbsDatabase = climbRepository.findClimbsByClimbUuid(climb.getClimbUuid());
        List<Climb> climbsDatabase = optionalClimbsDatabase.orElseGet(ArrayList::new);
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
