package com.pvpgame.exception;

public class PlayerAlreadySelectedException extends RuntimeException{

    public PlayerAlreadySelectedException(Long playerId){
        super("Player with ID " + playerId + " is already selected.");
    }
}
