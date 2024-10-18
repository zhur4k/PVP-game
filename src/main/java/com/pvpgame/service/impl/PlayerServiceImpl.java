package com.pvpgame.service.impl;

import com.pvpgame.dto.PlayerContextDTO;
import com.pvpgame.dto.PlayerToChooseDTO;
import com.pvpgame.dto.mapper.PlayerContextDTOMapper;
import com.pvpgame.dto.mapper.PlayerToChooseDTOMapper;
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
    private final PlayerContextDTOMapper playerContextDTOMapper;
    private final PlayerToChooseDTOMapper playerToChooseDTOMapper;

    @Override
    public List<PlayerToChooseDTO> getAllFreePlayers() {
        return playerRepository.findAllByLockedBy(null)
                .stream()
                .map(playerToChooseDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PlayerContextDTO selectPlayer(Long playerId, String sessionId) {

        long count = playerRepository.countByLockedBy(sessionId);
        if (count > 0) {
            throw new PlayerAlreadySelectedByUserException(playerId);
        }

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId.toString()));

        if (player.getLockedBy() != null) {
            if (player.getLockedBy().equals(sessionId)) {
                throw new PlayerAlreadySelectedByUserException(playerId);
            }
            throw new PlayerAlreadySelectedException(playerId);
        }

        player.setLockedBy(sessionId);

        playerRepository.save(player);

        return playerContextDTOMapper.apply(player);
    }

    @Override
    @Transactional
    public void unlockPlayer(String sessionId) {
        Player player = playerRepository.findByLockedBy(sessionId)
                .orElseThrow(() -> new PlayerNotFoundException(sessionId));

        player.setLockedBy(null);
        playerRepository.save(player);
    }

    @Override
    public PlayerContextDTO getPlayerContext(String sessionId) {

        Player player = playerRepository.findByLockedBy(sessionId)
                .orElseThrow(() -> new PlayerNotFoundException(sessionId));

        return playerContextDTOMapper.apply(player);
    }

    @Override
    @Transactional
    public PlayerContextDTO movePlayer(String sessionId, Direction direction) {
        Player player = playerRepository.findBasicByLockedBy(sessionId)
                .orElseThrow(() -> new PlayerNotFoundException(sessionId));

        Location location = player.getLocation();
        Location newLocation = location.getNeighbors().get(direction);

        if(newLocation == null){
            throw new LocationNotFoundException(location.getId(), direction);
        }

        player.setLocation(newLocation);
        playerRepository.save(player);

        Player updatedPlayer = playerRepository.findByLockedBy(sessionId)
                .orElseThrow(() -> new PlayerNotFoundException(sessionId));

        return playerContextDTOMapper.apply(updatedPlayer);
    }
}
