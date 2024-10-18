package com.pvpgame.exception;

public class BattleNotFoundForPlayerException extends RuntimeException{

    public BattleNotFoundForPlayerException(String message){
        super(message);
    }
}
