package com.pvpgame.config;

import com.pvpgame.model.Direction;
import com.pvpgame.model.Location;
import com.pvpgame.model.Player;
import com.pvpgame.repository.EnemyRepository;
import com.pvpgame.repository.LocationRepository;
import com.pvpgame.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final EnemyRepository enemyRepository;
    private final LocationRepository locationRepository;
    private final PlayerRepository playerRepository;


    @Override
    public void run(String... args) throws Exception {

        Location location1 = Location.builder()
                .x(0)
                .y(0)
                .build();

        Location location2 = Location.builder()
                .x(0)
                .y(1)
                .build();

        Location location3 = Location.builder()
                .x(1)
                .y(0)
                .build();

        location1.getNeighbors().put(Direction.UP, location2);
        location1.getNeighbors().put(Direction.RIGHT, location3);

        location2.getNeighbors().put(Direction.DOWN, location1);
        location3.getNeighbors().put(Direction.LEFT, location1);

        locationRepository.saveAll(Arrays.asList(location1, location2, location3));

        Player player1 = Player.builder()
                .name("TestName1")
                .location(location1)
                .build();

        playerRepository.save(player1);

        Player player2 = Player.builder()
                .name("TestName2")
                .location(location2)
                .build();

        playerRepository.save(player2);

        Player player3 = Player.builder()
                .name("TestName3")
                .location(location3)
                .build();

        playerRepository.save(player3);
    }
}
