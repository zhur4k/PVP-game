package com.pvpgame.exception;

public class PlayerAlreadyInBattleException extends RuntimeException {

    public PlayerAlreadyInBattleException(String message) {
        super(message);
    }
}
