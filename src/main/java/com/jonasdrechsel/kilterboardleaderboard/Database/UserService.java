package com.jonasdrechsel.kilterboardleaderboard.Database;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import com.jonasdrechsel.kilterboardleaderboard.KilterExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final KilterExternalApiService kilterApi;

    @Autowired
    public UserService(UserRepository userRepository, KilterExternalApiService kilterApi, KilterExternalApiService kilterApi1){
        this.userRepository = userRepository;
        this.kilterApi = kilterApi1;
    }

    public KilterUser saveUser(KilterUser kilterUser) {
        return userRepository.save(kilterUser);
    }
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public List<KilterUser> getAll() {
        return userRepository.findAll();
    }
    public KilterUser[] searchUser(String name) {
        return kilterApi.searchUser(name);
    }
    public List<KilterUser> getOrderedByPp(){
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "pp"));
    }
//    public void updatePp(KilterUser kilterUser, int pp) {
//        Optional<KilterUser> databaseUser = userRepository.findById(kilterUser.getId());
//        if (databaseUser.isPresent()) {
//            KilterUser presentUser = databaseUser.get();
//            userRepository.save(kilterUser);
//        }
//        else {
//            throw new Error("When saving newly calculated pp user was not present in the database. :(");
//        }
//    }

}

