package com.pvpgame.controller;

import com.pvpgame.service.BattleService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/battle")
@OpenAPIDefinition(info = @Info(title = "PVP-Game API", version = "1.0"))
@Tag(name = "Battle Controller", description = "API to manage battles and their actions")
public class BattleController {

    private final BattleService battleService;

    @Operation(summary = "Start battle", description = "Initiate a battle between the player and an enemy.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Battle started successfully."),
            @ApiResponse(responseCode = "404", description = "Player not found."),
            @ApiResponse(responseCode = "409", description = "Player is already in a battle."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/start")
    public ResponseEntity<String> startBattle(HttpSession session){
        battleService.startBattle(session.getId());
        return ResponseEntity.ok("Battle started!");
    }

    @Operation(summary = "End battle", description = "End the ongoing battle between the player and an enemy.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Battle ended successfully."),
            @ApiResponse(responseCode = "404", description = "Player not found."),
            @ApiResponse(responseCode = "409", description = "Player is not currently in a battle."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/end")
    public ResponseEntity<String> endBattle(HttpSession session){
        battleService.endBattle(session.getId());
        return ResponseEntity.ok("Battle ended!");
    }

    @Operation(summary = "Apologize", description = "The player apologizes to the enemy, ending the battle peacefully.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player apologized and the battle ended peacefully."),
            @ApiResponse(responseCode = "404", description = "Player not found."),
            @ApiResponse(responseCode = "409", description = "Player is not currently in a battle."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/apologize")
    public ResponseEntity<String> apologize(HttpSession session){
        battleService.apologize(session.getId());
        return ResponseEntity.ok("Battle ended! You apologized.");
    }
}
