package com.jonasdrechsel.kilterboardleaderboard;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import com.jonasdrechsel.kilterboardleaderboard.Database.ClimbService;
import com.jonasdrechsel.kilterboardleaderboard.Database.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kilter")
public class LeaderboardController {
    private final UserService userService;
    private final ClimbService climbService;
    final HttpHeaders httpHeaders;
    @Autowired
    public LeaderboardController (KilterExternalApiService kilterApi, UserService userService, ClimbService climbService) {
        this.userService = userService;
        this.climbService = climbService;
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<KilterUser[]> findUser(@PathVariable("name") String name) {
        return new ResponseEntity<KilterUser[]>(userService.searchUser(name), httpHeaders, HttpStatus.OK);
    }
    @GetMapping("/leaderboard")
    public ResponseEntity<List<KilterUser>> getLeaderboard() {

        return new ResponseEntity<>(userService.getAll(), httpHeaders, HttpStatus.OK);
    }
    @GetMapping("/climbs/{id}")
    public ResponseEntity<List<Climb>> getClimbs(@PathVariable("id") int id) {
        return new ResponseEntity<>(climbService.getClimbs(id), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/user/add")
    public ResponseEntity<KilterUser> addUser(@RequestBody KilterUser kilterUser) {
        // get all climbed grades
        climbService.updateClimbs(kilterUser);
        // calculate pp
        return new ResponseEntity<KilterUser>(userService.saveUser(kilterUser), HttpStatus.OK);
    }

    @DeleteMapping("/user/remove/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable("id") long id) {
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