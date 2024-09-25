package com.jonasdrechsel.kilterboardleaderboard.Database;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClimbRepository extends JpaRepository<Climb, String> {
    List<Climb> findClimbsByUserIdOrderByPpDesc(long userId);
    void removeClimbsByUserId(long userId);
    List<Climb> findClimbsByClimbUuid(String uuid);
}
