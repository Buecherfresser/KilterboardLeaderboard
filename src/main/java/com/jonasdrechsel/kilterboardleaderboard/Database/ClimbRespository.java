package com.jonasdrechsel.kilterboardleaderboard.Database;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import com.jonasdrechsel.kilterboardleaderboard.Data.KilterUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClimbRespository extends JpaRepository<Climb, String> {
    List<Climb> findByUserIdOrderByPpDesc(long userId);
}
