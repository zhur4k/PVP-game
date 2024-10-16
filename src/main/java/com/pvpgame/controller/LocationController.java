package com.pvpgame.controller;

import com.pvpgame.model.Enemy;
import com.pvpgame.model.Player;
import com.pvpgame.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("location")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{locationId}/players")
    public ResponseEntity<List<Player>> getPlayersAtLocation(
            @PathVariable("locationId") Long locationId){
        return ResponseEntity.ok(locationService.getPlayersAtLocation(locationId));
    }

    @GetMapping("/{locationId}/enemies")
    public ResponseEntity<List<Enemy>> getEnemiesAtLocation(
            @PathVariable("locationId") Long locationId){
        return ResponseEntity.ok(locationService.getEnemiesAtLocation(locationId));
    }
}
