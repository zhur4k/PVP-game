package com.pvpgame.service;

import com.pvpgame.dto.PlayerAtLocationDTO;

import java.util.List;

public interface LocationService {

    List<PlayerAtLocationDTO> getPlayersAtLocation(Long locationId);
}
