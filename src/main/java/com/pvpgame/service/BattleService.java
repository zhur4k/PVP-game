package com.pvpgame.service;

public interface BattleService {

    void startBattle(String sessionId);

    void apologize(String sessionId);

    void endBattle(String sessionId);
}
