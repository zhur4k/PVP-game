package com.pvpgame.dto.mapper;

import com.pvpgame.dto.LocationDTO;
import com.pvpgame.model.Enemy;
import com.pvpgame.model.Location;
import com.pvpgame.model.Player;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LocationDtoMapper implements Function<Location, LocationDTO> {
    @Override
    public LocationDTO apply(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getX(),
                location.getY(),
                location.getNeighbors().entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue() != null ? entry.getValue().getId() : null
                        )),
                location.getPlayers().stream()
                        .map(Player::getId)
                        .collect(Collectors.toList()),
                location.getEnemies().stream()
                        .map(Enemy::getId)
                        .collect(Collectors.toList())
        );
    }
}
