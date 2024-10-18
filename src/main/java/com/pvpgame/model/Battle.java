package com.pvpgame.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_battle")
public class Battle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BattleState battleState;

    @OneToOne(fetch = FetchType.LAZY)
    private Player player;

    @OneToOne(fetch = FetchType.LAZY)
    private Enemy enemy;
}
