package com.pvpgame.service;

import com.pvpgame.dto.PlayerContextDTO;
import com.pvpgame.dto.PlayerToChooseDTO;
import com.pvpgame.model.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService {

    List<PlayerToChooseDTO> getAllFreePlayers();

    PlayerContextDTO selectPlayer(Long playerId, String sessionId);

    PlayerContextDTO getPlayerContext(String sessionId);

    PlayerContextDTO  movePlayer(String sessionId, Direction direction);

    void unlockPlayer(String sessionId);
}
