package com.pvpgame.controller;

import com.pvpgame.dto.EnemyDto;
import com.pvpgame.dto.LocationDto;
import com.pvpgame.dto.PlayerDto;
import com.pvpgame.service.LocationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@OpenAPIDefinition(info = @Info(title = "PVP-Game API", version = "1.0"))
@Tag(name = "Location Controller", description = "API to manage locations and their entities (players, enemies)")
public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "Get all locations", description = "Fetches a list of all available locations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of locations"),
            @ApiResponse(responseCode = "204", description = "No content available"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations(){
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @Operation(summary = "Get players at a location", description = "Fetches a list of players present at a specific location.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of players"),
            @ApiResponse(responseCode = "204", description = "No content available"),
            @ApiResponse(responseCode = "404", description = "Location not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{locationId}/players")
    public ResponseEntity<List<PlayerDto>> getPlayersAtLocation(
            @PathVariable("locationId") Long locationId){
        return ResponseEntity.ok(locationService.getPlayersAtLocation(locationId));
    }

    @Operation(summary = "Get enemies at a location", description = "Fetches a list of enemies present at a specific location.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of enemies"),
            @ApiResponse(responseCode = "204", description = "No content available"),
            @ApiResponse(responseCode = "404", description = "Location not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{locationId}/enemies")
    public ResponseEntity<List<EnemyDto>> getEnemiesAtLocation(
            @PathVariable("locationId") Long locationId){
        return ResponseEntity.ok(locationService.getEnemiesAtLocation(locationId));
    }
}
