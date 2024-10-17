package com.pvpgame.dto.mapper;

import com.pvpgame.dto.LocationDto;
import com.pvpgame.model.Location;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LocationDtoMapper implements Function<Location, LocationDto> {
    @Override
    public LocationDto apply(Location location) {
        return new LocationDto(
                location.getId(),
                location.getX(),
                location.getY(),
                location.getPlayers()
                        .stream()
                        .map(player -> player.getId())
                        .collect(Collectors.toList()),
                location.getEnemies()
                        .stream()
                        .map(enemy -> enemy.getId())
                        .collect(Collectors.toList())
        );
    }
}
