package com.pvpgame.service.impl;

import com.pvpgame.exception.*;
import com.pvpgame.model.Direction;
import com.pvpgame.model.Location;
import com.pvpgame.model.Player;
import com.pvpgame.repository.PlayerRepository;
import com.pvpgame.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public void selectPlayer(Long playerId, String sessionId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        if(player.getLockedBy().equals(sessionId)){
            throw new PlayerAlreadySelectedByUserException(playerId);
        }

        if(player.getLockedBy() != null){
            throw new PlayerAlreadySelectedException(playerId);
        }

        player.setLockedBy(sessionId);

        playerRepository.save(player);
    }

    @Override
    public void unlockPlayer(Long playerId, String sessionId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        verifyAccess(player, sessionId);

        player.setLockedBy(null);
        playerRepository.save(player);
    }

    @Override
    public Player getPlayer(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public void verifyAccess(Player player, String sessionId) {
        if (player.getLockedBy() == null) {
            throw new UnauthorizedAccessException("Player is not currently locked.");
        }

        if (!sessionId.equals(player.getLockedBy())) {
            throw new UnauthorizedAccessException("You are not authorized to perform this action on the player.");
        }
    }

    @Override
    public Player movePlayer(Long playerId, Direction direction, String sessionId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        verifyAccess(player, sessionId);

        Location location = player.getLocation();
        Location newLocation = location.getNeighbors().get(direction);

        if(newLocation == null){
            throw new LocationNotFoundException(location.getId(), direction);
        }

        player.setLocation(newLocation);

        return playerRepository.save(player);
    }
}
