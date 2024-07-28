package com.jonasdrechsel.kilterboardleaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/kilter")
public class LeaderboardController {
    @Autowired
    private KilterExternalApiService kilterApi;

    @GetMapping("/find/{name}")
    public ResponseEntity<String> findUser(@PathVariable("name") String name) {
        System.out.println(name);
        return ResponseEntity.ok(name);
    }
}