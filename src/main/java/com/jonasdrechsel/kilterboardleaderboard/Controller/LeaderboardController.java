package com.jonasdrechsel.kilterboardleaderboard.Controller;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import com.jonasdrechsel.kilterboardleaderboard.Database.ClimbService;
import com.jonasdrechsel.kilterboardleaderboard.Database.LeaderboardService;
import com.jonasdrechsel.kilterboardleaderboard.Database.UserService;
import com.jonasdrechsel.kilterboardleaderboard.KilterExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/kilter")
public class LeaderboardController {
    private final UserService userService;
    private final ClimbService climbService;
    private final LeaderboardService leaderboardService;
    final HttpHeaders httpHeaders;

    @Autowired
    public LeaderboardController(KilterExternalApiService kilterApi, UserService userService, ClimbService climbService, LeaderboardService leaderboardService) {
        this.userService = userService;
        this.climbService = climbService;
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<KilterUser[]> findUser(@PathVariable("name") String name) {
        return new ResponseEntity<KilterUser[]>(userService.searchUser(name), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<KilterUser>> getLeaderboard() {
        return new ResponseEntity<>(userService.getOrderedByPp(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/leaderboard/refresh")
    public ResponseEntity<List<KilterUser>> refreshLeaderboard() {
        leaderboardService.refreshLeaderboard();

        return new ResponseEntity<>(leaderboardService.getLeaderboard(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/climbs/{id}")
    public ResponseEntity<List<Climb>> getClimbs(@PathVariable("id") int id) {
        return new ResponseEntity<>(climbService.getClimbs(id), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/user/add")
    public ResponseEntity<KilterUser> addUser(@RequestBody KilterUser kilterUser) {

        return new ResponseEntity<KilterUser>(leaderboardService.addUser(kilterUser), HttpStatus.OK);
    }

    @DeleteMapping("/user/remove/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable("id") long id) {
        climbService.removeClimbs(id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/leaderboard")
//    public ResponseEntity<List<>> getLeaderboard() {
//        // get users in leaderboard
//        List<KilterUser> users = userService.getAll();
//        // fetch data for each user and create sorted leaderboard
//    }
}