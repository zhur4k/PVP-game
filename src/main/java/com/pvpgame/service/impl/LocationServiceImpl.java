package com.pvpgame.service.impl;

import com.pvpgame.dto.EnemyDto;
import com.pvpgame.dto.LocationDto;
import com.pvpgame.dto.PlayerDto;
import com.pvpgame.dto.mapper.EnemyDtoMapper;
import com.pvpgame.dto.mapper.LocationDtoMapper;
import com.pvpgame.dto.mapper.PlayerDtoMapper;
import com.pvpgame.exception.NoContentException;
import com.pvpgame.model.Enemy;
import com.pvpgame.model.Location;
import com.pvpgame.model.Player;
import com.pvpgame.repository.LocationRepository;
import com.pvpgame.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final PlayerDtoMapper playerDtoMapper;
    private final EnemyDtoMapper enemyDtoMapper;
    private final LocationDtoMapper locationDtoMapper;

    @Override
    public List<PlayerDto> getPlayersAtLocation(Long locationId) {
        List<Player> players = locationRepository.findPlayersByLocationId(locationId);

        if(players.isEmpty()){
            throw new NoContentException("No players found at location ID: " + locationId);
        }

        return players
                .stream()
                .map(playerDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnemyDto> getEnemiesAtLocation(Long locationId) {
        List<Enemy> enemies = locationRepository.findEnemiesByLocationId(locationId);

        if(enemies.isEmpty()){
            throw new NoContentException("No enemies found at location ID: " + locationId);
        }

        return enemies
                .stream()
                .map(enemyDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationDto> getAllLocations() {
        List<Location> locations = locationRepository.findAll();

        if(locations.isEmpty()){
            throw new NoContentException("No locations found");
        }

        return locations
                .stream()
                .map(locationDtoMapper)
                .collect(Collectors.toList());
    }
}
