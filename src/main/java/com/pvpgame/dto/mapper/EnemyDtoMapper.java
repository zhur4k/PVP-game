package com.pvpgame.dto.mapper;

import com.pvpgame.dto.EnemyDto;
import com.pvpgame.model.Enemy;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EnemyDtoMapper implements Function<Enemy, EnemyDto> {
    @Override
    public EnemyDto apply(Enemy enemy) {
        return new EnemyDto(
                enemy.getId(),
                enemy.getLocation().getId()
        );
    }
}
