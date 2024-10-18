package com.pvpgame.dto.mapper;

import com.pvpgame.dto.PlayerAtLocationDTO;
import com.pvpgame.model.Player;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlayerAtLocationDTOMapper implements Function<Player, PlayerAtLocationDTO> {
    @Override
    public PlayerAtLocationDTO apply(Player player) {
        return new PlayerAtLocationDTO(
                player.getId(),
                player.getName()
        );
    }
}
