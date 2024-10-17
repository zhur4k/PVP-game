package com.pvpgame.controller;

import com.pvpgame.dto.PlayerDto;
import com.pvpgame.model.Direction;
import com.pvpgame.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAllPlayers(){
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerDto> getPlayer(
            @PathVariable Long playerId){
        return ResponseEntity.ok(playerService.getPlayer(playerId));
    }

    @GetMapping("/{playerId}/select")
    public ResponseEntity<Void> selectPlayer(
            @PathVariable Long playerId, HttpSession session){
        playerService.selectPlayer(playerId, session.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{playerId}/move")
    public ResponseEntity<PlayerDto> movePlayer(
            @PathVariable Long playerId, @RequestParam Direction direction, HttpSession session){
        return ResponseEntity.ok(playerService.movePlayer(playerId,direction,session.getId()));
    }

    @GetMapping("/{playerId}/unlock")
    public ResponseEntity<Void> unlockPlayer(
            @PathVariable Long playerId, HttpSession session){
        playerService.unlockPlayer(playerId, session.getId());
        return ResponseEntity.ok().build();
    }
}
