package com.pvpgame.repository;

import com.pvpgame.model.Enemy;
import com.pvpgame.model.Location;
import com.pvpgame.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT p FROM Player p WHERE p.location.id = :locationId")
    List<Player> findPlayersByLocationId(@Param("locationId") Long locationId);

    @Query("SELECT e FROM Enemy e WHERE e.location.id = :locationId")
    List<Enemy> findEnemiesByLocationId(@Param("locationId") Long locationId);
}
