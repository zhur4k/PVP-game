package com.pvpgame.dto.mapper;

import com.pvpgame.dto.PlayerToChooseDTO;
import com.pvpgame.model.Player;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlayerToChooseDTOMapper implements Function<Player, PlayerToChooseDTO> {
    @Override
    public PlayerToChooseDTO apply(Player player) {
        return new PlayerToChooseDTO(
                player.getId(),
                player.getName(),
                player.getLockedBy()
        );
    }
}
