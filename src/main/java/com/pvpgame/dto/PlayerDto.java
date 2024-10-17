package com.pvpgame.dto;

public record PlayerDto(
        Long id,

        String name,

        String lockedBy,

        Long locationId
) {
}
