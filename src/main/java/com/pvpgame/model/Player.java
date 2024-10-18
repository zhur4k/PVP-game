package com.pvpgame.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lockedBy;

    private boolean shouldBattle;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;
}
