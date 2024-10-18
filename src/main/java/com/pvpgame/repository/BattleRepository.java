package com.pvpgame.repository;

import com.pvpgame.model.Battle;
import com.pvpgame.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {

    boolean existsByPlayer(Player player);

    Optional<Battle> findByPlayer(Player player);
}
