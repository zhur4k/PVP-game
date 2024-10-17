package com.pvpgame.dto;

import java.util.List;

public record LocationDto(
        Long id,
        int x,
        int y,
        List<Long> players,
        List<Long> enemies
) {
}
