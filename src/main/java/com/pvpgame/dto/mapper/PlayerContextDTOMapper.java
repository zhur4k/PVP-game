package com.pvpgame.dto.mapper;

import com.pvpgame.dto.PlayerContextDTO;
import com.pvpgame.model.Location;
import com.pvpgame.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PlayerContextDTOMapper implements Function<Player, PlayerContextDTO> {
    private final LocationDtoMapper locationDtoMapper;

    @Override
    public PlayerContextDTO apply(Player player) {
        Location location = player.getLocation();
        return new PlayerContextDTO(
                player.getId(),
                player.getName(),
                player.isShouldBattle(),
                locationDtoMapper.apply(location)
        );
    }
}
