package com.pvpgame.dto;

public record PlayerContextDTO(
        Long id,

        String name,

        LocationDTO location
) {
}
