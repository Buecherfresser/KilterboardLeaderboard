package com.jonasdrechsel.kilterboardleaderboard.Database;

import com.jonasdrechsel.kilterboardleaderboard.Data.Climb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClimbRepository extends JpaRepository<Climb, String> {
    Optional<List<Climb>> findClimbsByUserIdOrderByPpDesc(long userId);

    void removeClimbsByUserId(long userId);

    Optional<List<Climb>> findClimbsByClimbUuid(String uuid);
}
