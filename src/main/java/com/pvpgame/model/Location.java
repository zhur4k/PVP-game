package com.pvpgame.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private int x;
    private int y;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "location_neighbors",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "neighbor_location_id")
    )
    @MapKeyColumn(name = "direction")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Direction, Location> neighbors = new HashMap<>();

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Enemy> enemies = new ArrayList<>();
}
