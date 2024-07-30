package com.jonasdrechsel.kilterboardleaderboard;

import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import com.jonasdrechsel.kilterboardleaderboard.Database.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kilter")
public class LeaderboardController {
    private final KilterExternalApiService kilterApi;
    private final UserService userService;
    @Autowired
    public LeaderboardController (KilterExternalApiService kilterApi, UserService userService) {
        this.kilterApi = kilterApi;
        this.userService = userService;
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<KilterUser[]> findUser(@PathVariable("name") String name) {
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<KilterUser[]>(kilterApi.searchUser(name), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<KilterUser> addUser(@RequestBody KilterUser kilterUser) {
        return new ResponseEntity<KilterUser>(userService.saveUser(kilterUser), HttpStatus.OK);
    }
}