package com.pvpgame.service;

import com.pvpgame.dto.EnemyDto;
import com.pvpgame.dto.LocationDto;
import com.pvpgame.dto.PlayerDto;

import java.util.List;

public interface LocationService {

    List<PlayerDto> getPlayersAtLocation(Long locationId);

    List<EnemyDto> getEnemiesAtLocation(Long locationId);

    List<LocationDto> getAllLocations();
}
