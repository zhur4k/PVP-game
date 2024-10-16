package com.pvpgame.service;

import com.pvpgame.model.Direction;
import com.pvpgame.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService {

    void selectPlayer(Long playerId, String sessionId);

    void unlockPlayer(Long playerId, String sessionId);

    Player getPlayer(Long playerId);

    List<Player> getAllPlayers();

    void verifyAccess(Player player, String sessionId);

    Player movePlayer(Long playerId, Direction direction, String sessionId);
}
