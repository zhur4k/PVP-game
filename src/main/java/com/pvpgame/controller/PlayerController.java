package com.pvpgame.controller;

import com.pvpgame.dto.PlayerContextDTO;
import com.pvpgame.dto.PlayerToChooseDTO;
import com.pvpgame.model.Direction;
import com.pvpgame.service.PlayerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
@OpenAPIDefinition(info = @Info(title = "PVP-Game API", version = "1.0"))
@Tag(name = "Player Controller", description = "API to manage players and their actions")
public class PlayerController {

    private final PlayerService playerService;

    @Operation(summary = "Get all players", description = "Fetches a list of all players.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of players"),
            @ApiResponse(responseCode = "204", description = "No content available"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PlayerToChooseDTO>> getAllPlayers(){
        return ResponseEntity.ok(playerService.getAllFreePlayers());
    }

    @Operation(summary = "Select a player", description = "Selects a player by their ID and locks them for the current session.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player successfully selected"),
            @ApiResponse(responseCode = "404", description = "Player not found"),
            @ApiResponse(responseCode = "409", description = "Player already selected by another user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{playerId}/select")
    public ResponseEntity<PlayerContextDTO> selectPlayer(
            @PathVariable Long playerId, HttpSession session){
        return ResponseEntity.ok(playerService.selectPlayer(playerId, session.getId()));
    }

    @Operation(summary = "Get a client player", description = "Fetches details of a client player by client sessionId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved player details"),
            @ApiResponse(responseCode = "404", description = "Player not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/player")
    public ResponseEntity<PlayerContextDTO> getPlayerContext(
            HttpSession session){
        return ResponseEntity.ok(playerService.getPlayerContext(session.getId()));
    }

    @Operation(summary = "Move player", description = "Moves the player in the specified direction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player successfully moved"),
            @ApiResponse(responseCode = "404", description = "Player or destination not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/move")
    public ResponseEntity<PlayerContextDTO> movePlayer(
            @RequestParam Direction direction, HttpSession session){
        return ResponseEntity.ok(playerService.movePlayer(session.getId(), direction));
    }

    @Operation(summary = "Unlock a player", description = "Unlocks the player, allowing others to select them.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player successfully unlocked"),
            @ApiResponse(responseCode = "404", description = "Player not found"),
            @ApiResponse(responseCode = "403", description = "Access denied to unlock the player"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/unlock")
    public ResponseEntity<Void> unlockPlayer(
            HttpSession session){
        playerService.unlockPlayer(session.getId());
        return ResponseEntity.ok().build();
    }
}
