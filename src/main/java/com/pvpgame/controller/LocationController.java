package com.pvpgame.controller;

import com.pvpgame.dto.EnemyDto;
import com.pvpgame.dto.LocationDto;
import com.pvpgame.dto.PlayerDto;
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
@RequestMapping("locations")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations(){
        return ResponseEntity.ok(locationService.getAllLocations());
    }
    @GetMapping("/{locationId}/players")
    public ResponseEntity<List<PlayerDto>> getPlayersAtLocation(
            @PathVariable("locationId") Long locationId){
        return ResponseEntity.ok(locationService.getPlayersAtLocation(locationId));
    }

    @GetMapping("/{locationId}/enemies")
    public ResponseEntity<List<EnemyDto>> getEnemiesAtLocation(
            @PathVariable("locationId") Long locationId){
        return ResponseEntity.ok(locationService.getEnemiesAtLocation(locationId));
    }
}
