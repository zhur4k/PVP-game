package com.pvpgame.service;

import com.pvpgame.model.Player;

public interface BattleService {

    void startBattle(String sessionId);

    void apologize(String sessionId);

    void endBattle(String sessionId);

    void checkBattleTarget(Player player);
}
