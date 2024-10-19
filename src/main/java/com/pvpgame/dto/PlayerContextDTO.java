package com.pvpgame.dto;

public record PlayerContextDTO(
        Long id,

        String name,

        boolean shouldBattle,

        LocationDTO location
) {
}
