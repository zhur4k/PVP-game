package com.pvpgame.dto;

import com.pvpgame.model.Direction;

import java.util.List;
import java.util.Map;

public record LocationDTO(
        Long id,

        int x,

        int y,

        Map<Direction, Long> neighbors,

        List<Long> players,

        List<Long> enemies
) {
}
