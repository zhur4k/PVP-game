package com.pvpgame.dto;

public record PlayerToChooseDTO(
        Long id,

        String name,

        String lockedBy
) {
}
