package com.pvpgame.service.impl;

import com.pvpgame.dto.PlayerAtLocationDTO;
import com.pvpgame.dto.mapper.PlayerAtLocationDTOMapper;
import com.pvpgame.model.Player;
import com.pvpgame.repository.LocationRepository;
import com.pvpgame.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final PlayerAtLocationDTOMapper playerAtLocationDTOMapper;

    @Override
    public List<PlayerAtLocationDTO> getPlayersAtLocation(Long locationId) {
        List<Player> players = locationRepository.findPlayersByLocationId(locationId);

        return players
                .stream()
                .map(playerAtLocationDTOMapper)
                .collect(Collectors.toList());
    }
}
