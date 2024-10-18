package com.pvpgame.service.impl;

import com.pvpgame.exception.*;
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

        checkBattleTarget(player);

        if (battleRepository.existsByPlayerAndBattleState(player, BattleState.IN_BATTLE)) {
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

        checkBattleTarget(player);

        if (battleRepository.existsByPlayerAndBattleState(player, BattleState.IN_BATTLE)) {
            throw new PlayerAlreadyInBattleException("Player is already in battle.");
        }

        Enemy enemy = player.getLocation().getEnemy();
        if (enemy == null) {
            throw new EnemyNotFoundException("No enemy to fight at this location.");
        }

        Battle battle = Battle
                .builder()
                .battleState(BattleState.ESCAPED)
                .player(player)
                .enemy(enemy)
                .build();

        battleRepository.save(battle);

        player.setShouldBattle(false);
        playerRepository.save(player);
    }

    @Override
    @Transactional
    public void endBattle(String sessionId) {
        Player player = playerRepository.findSimpleByLockedBy(sessionId)
                .orElseThrow(() -> new PlayerNotFoundException(sessionId));

        checkBattleTarget(player);

        Battle battle = battleRepository.findByPlayerAndBattleState(player, BattleState.IN_BATTLE)
                .orElseThrow(() -> new BattleNotFoundForPlayerException("Player is not currently in battle."));

        battle.setBattleState(BattleState.FINISH_BATTLE);
        battleRepository.save(battle);
        player.setShouldBattle(false);
        playerRepository.save(player);
    }

    @Override
    public void checkBattleTarget(Player player) {
        if(!player.isShouldBattle()){
            throw new NoBattleTargetException("Cannot initiate battle as there is no target.");
        }
    }
}
