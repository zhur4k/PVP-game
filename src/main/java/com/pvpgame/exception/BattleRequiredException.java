package com.pvpgame.exception;

public class BattleRequiredException extends RuntimeException{
    public BattleRequiredException(String message){
        super(message);
    }
}
