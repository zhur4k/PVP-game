package com.pvpgame.repository;

import com.pvpgame.model.Player;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @EntityGraph(attributePaths = {"location", "location.neighbors", "location.players", "location.enemy"})
    Optional<Player> findById(Long playerId);

    @EntityGraph(attributePaths = {"location", "location.enemy"})
    Optional<Player> findSimpleByLockedBy(String sessionId);

    List<Player> findAllByLockedBy(String sessionId);

    @EntityGraph(attributePaths = {"location", "location.neighbors", "location.players", "location.enemy"})
    Optional<Player> findByLockedBy(String sessionId);

    @EntityGraph(attributePaths = {"location", "location.neighbors"})
    Optional<Player> findBasicByLockedBy(String sessionId);

    long countByLockedBy(String sessionId);
}
