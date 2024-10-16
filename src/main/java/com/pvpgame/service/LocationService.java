package com.pvpgame.service;

import com.pvpgame.model.Enemy;
import com.pvpgame.model.Player;

import java.util.List;

public interface LocationService {

    List<Player> getPlayersAtLocation(Long locationId);

    List<Enemy> getEnemiesAtLocation(Long locationId);
}
