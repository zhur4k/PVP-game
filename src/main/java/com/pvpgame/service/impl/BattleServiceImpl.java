package com.pvpgame.service.impl;

import com.pvpgame.exception.BattleNotFoundForPlayerException;
import com.pvpgame.exception.EnemyNotFoundException;
import com.pvpgame.exception.PlayerAlreadyInBattleException;
import com.pvpgame.exception.PlayerNotFoundException;
import com.pvpgame.model.Battle;
import com.pvpgame.model.BattleState;
import com.pvpgame.model.Enemy;
import com.pvpgame.model.Player;
import com.pvpgame.repository.BattleRepository;
import com.pvpgame.repository.PlayerRepository;
import com.pvpgame.service.BattleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

    private final BattleRepository battleRepository;
    private final PlayerRepository playerRepository;

    @Override
    @Transactional
    public void startBattle(String sessionId) {
        Player player = playerRepository.findSimpleByLockedBy(sessionId)
                .orElseThrow(() -> new PlayerNotFoundException(sessionId));

        if (battleRepository.existsByPlayer(player)) {
            throw new PlayerAlreadyInBattleException("Player is already in battle.");
        }

        Enemy enemy = player.getLocation().getEnemy();
        if (enemy == null) {
            throw new EnemyNotFoundException("No enemy to fight at this location.");
        }

        Battle battle = Battle
                .builder()
                .battleState(BattleState.IN_BATTLE)
                .player(player)
                .enemy(enemy)
                .build();

        battleRepository.save(battle);
    }

    @Override
    @Transactional
    public void apologize(String sessionId) {
        Player player = playerRepository.findSimpleByLockedBy(sessionId)
                .orElseThrow(() -> new PlayerNotFoundException(sessionId));

        Battle battle = battleRepository.findByPlayer(player)
                .orElseThrow(() -> new BattleNotFoundForPlayerException("Player is not currently in battle."));

        battle.setBattleState(BattleState.ESCAPED);
        battleRepository.save(battle);
    }

    @Override
    @Transactional
    public void endBattle(String sessionId) {
        Player player = playerRepository.findSimpleByLockedBy(sessionId)
                .orElseThrow(() -> new PlayerNotFoundException(sessionId));

        Battle battle = battleRepository.findByPlayer(player)
                .orElseThrow(() -> new BattleNotFoundForPlayerException("Player is not currently in battle."));

        battle.setBattleState(BattleState.FINISH_BATTLE);
        battleRepository.save(battle);
    }
}
