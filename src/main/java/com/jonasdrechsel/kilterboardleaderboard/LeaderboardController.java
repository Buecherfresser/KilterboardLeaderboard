package com.jonasdrechsel.kilterboardleaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/kilter")
public class LeaderboardController {
    private final KilterExternalApiService kilterApi;

    @Autowired
    public LeaderboardController (KilterExternalApiService kilterApi) {
        this.kilterApi = kilterApi;
    }

    @GetMapping("/find/{name}")
    public Mono<String> findUser(@PathVariable("name") String name) {
        return kilterApi.searchUser(name);
    }
}