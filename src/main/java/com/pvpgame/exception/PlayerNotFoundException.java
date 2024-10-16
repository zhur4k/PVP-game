package com.pvpgame.exception;

public class PlayerNotFoundException extends RuntimeException{

    public PlayerNotFoundException(Long playerId) {
        super("Player with ID " + playerId + " not found.");
    }
}
