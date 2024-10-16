package com.pvpgame.exception;

public class PlayerAlreadySelectedByUserException extends RuntimeException{

    public PlayerAlreadySelectedByUserException(Long playerId){
        super("Player with ID " + playerId + " is already selected by the current user.");
    }
}
