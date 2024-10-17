package com.pvpgame.service.impl;

import com.pvpgame.dto.PlayerDto;
import com.pvpgame.dto.mapper.PlayerDtoMapper;
import com.pvpgame.exception.*;
import com.pvpgame.model.Direction;
import com.pvpgame.model.Location;
import com.pvpgame.model.Player;
import com.pvpgame.repository.PlayerRepository;
import com.pvpgame.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerDtoMapper playerDtoMapper;

    @Override
    @Transactional
    public void selectPlayer(Long playerId, String sessionId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        if (player.getLockedBy() != null) {
            if (player.getLockedBy().equals(sessionId)) {
                throw new PlayerAlreadySelectedByUserException(playerId);
            }
            throw new PlayerAlreadySelectedException(playerId);
        }

        player.setLockedBy(sessionId);

        playerRepository.save(player);
    }

    @Override
    @Transactional
    public void unlockPlayer(Long playerId, String sessionId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        verifyAccess(player, sessionId);

        player.setLockedBy(null);
        playerRepository.save(player);
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerDto getPlayer(Long playerId) {
        return playerDtoMapper.apply(playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerDto> getAllPlayers() {
        List<Player> players = playerRepository.findAll();

        if(players.isEmpty()){
            throw new NoContentException("No locations found");
        }

        return players
                .stream()
                .map(playerDtoMapper)
                .collect(Collectors.toList());
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
    @Transactional
    public PlayerDto movePlayer(Long playerId, Direction direction, String sessionId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        verifyAccess(player, sessionId);

        Location location = player.getLocation();
        Location newLocation = location.getNeighbors().get(direction);

        if(newLocation == null){
            throw new LocationNotFoundException(location.getId(), direction);
        }

        player.setLocation(newLocation);

        return playerDtoMapper.apply(playerRepository.save(player));
    }
}
