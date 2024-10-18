package com.pvpgame.config;

import com.pvpgame.model.Direction;
import com.pvpgame.model.Enemy;
import com.pvpgame.model.Location;
import com.pvpgame.model.Player;
import com.pvpgame.repository.EnemyRepository;
import com.pvpgame.repository.LocationRepository;
import com.pvpgame.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final EnemyRepository enemyRepository;
    private final LocationRepository locationRepository;
    private final PlayerRepository playerRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Location> locations = createLocations();
        locationRepository.saveAll(locations);

        List<Player> players = createPlayers(locations);
        playerRepository.saveAll(players);

        List<Enemy> enemies = createEnemies(locations);
        enemyRepository.saveAll(enemies);
    }

    private List<Location> createLocations() {
        Location location1 = Location.builder().x(0).y(0).build();
        Location location2 = Location.builder().x(0).y(1).build();
        Location location3 = Location.builder().x(1).y(0).build();
        Location location4 = Location.builder().x(1).y(1).build();
        Location location5 = Location.builder().x(0).y(2).build();
        Location location6 = Location.builder().x(2).y(0).build();

        location1.getNeighbors().put(Direction.UP, location2);
        location1.getNeighbors().put(Direction.RIGHT, location3);

        location2.getNeighbors().put(Direction.DOWN, location1);
        location2.getNeighbors().put(Direction.RIGHT, location4);
        location2.getNeighbors().put(Direction.UP, location5);

        location3.getNeighbors().put(Direction.LEFT, location1);
        location3.getNeighbors().put(Direction.RIGHT, location6);

        location4.getNeighbors().put(Direction.LEFT, location2);
        location4.getNeighbors().put(Direction.DOWN, location3);

        location5.getNeighbors().put(Direction.DOWN, location2);

        location6.getNeighbors().put(Direction.LEFT, location3);

        return Arrays.asList(location1, location2, location3, location4, location5, location6);
    }

    private List<Player> createPlayers(List<Location> locations) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            players.add(Player.builder()
                    .name("TestName" + (i + 1))
                    .location(locations.get(i))
                    .build());
        }
        return players;
    }

    private List<Enemy> createEnemies(List<Location> locations) {
        List<Enemy> enemies = new ArrayList<>();

        String[] enemyNames = {"Orc", "Goblin", "Dragon", "Troll"};
        int[] enemyStrengths = {50, 30, 100, 70};
        int[] locationIndices = {1, 2, 5, 3};

        for (int i = 0; i < enemyNames.length; i++) {
            enemies.add(Enemy.builder()
                    .name(enemyNames[i])
                    .strength(enemyStrengths[i])
                    .location(locations.get(locationIndices[i]))
                    .build());
        }

        return enemies;
    }
}
