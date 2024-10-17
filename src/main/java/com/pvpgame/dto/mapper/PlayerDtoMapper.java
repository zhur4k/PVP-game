package com.pvpgame.dto.mapper;

import com.pvpgame.dto.PlayerDto;
import com.pvpgame.model.Player;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlayerDtoMapper implements Function<Player, PlayerDto> {
    @Override
    public PlayerDto apply(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getName(),
                player.getLockedBy(),
                player.getLocation().getId()
        );
    }
}
