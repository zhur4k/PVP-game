package com.pvpgame.exception;

public class PlayerNotFoundException extends RuntimeException{

    public PlayerNotFoundException(String id) {
        super("Player " + id + " not found.");
    }
}
