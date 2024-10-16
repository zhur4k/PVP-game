package com.pvpgame.service.impl;

import com.pvpgame.exception.NoContentException;
import com.pvpgame.model.Enemy;
import com.pvpgame.model.Player;
import com.pvpgame.repository.LocationRepository;
import com.pvpgame.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<Player> getPlayersAtLocation(Long locationId) {
        List<Player> players = locationRepository.findPlayersByLocationId(locationId);

        if(players.isEmpty()){
            throw new NoContentException("No players found at location ID: " + locationId);
        }

        return players;
    }

    @Override
    public List<Enemy> getEnemiesAtLocation(Long locationId) {
        List<Enemy> enemies = locationRepository.findEnemiesByLocationId(locationId);

        if(enemies.isEmpty()){
            throw new NoContentException("No enemies found at location ID: " + locationId);
        }

        return enemies;
    }
}
