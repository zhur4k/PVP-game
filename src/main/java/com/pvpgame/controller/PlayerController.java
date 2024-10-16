package com.pvpgame.controller;

import com.pvpgame.model.Direction;
import com.pvpgame.model.Player;
import com.pvpgame.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/")
    public ResponseEntity<List<Player>> getPlayer(){
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<Player> getPlayer(
            @PathVariable Long playerId){
        return ResponseEntity.ok(playerService.getPlayer(playerId));
    }

    @GetMapping("/select/{playerId}")
    public ResponseEntity<Void> selectPlayer(
            @PathVariable Long playerId, HttpSession session){
        playerService.selectPlayer(playerId, session.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/move/{playerId}")
    public ResponseEntity<Player> movePlayer(
            @PathVariable Long playerId, @RequestParam Direction direction, HttpSession session){
        return ResponseEntity.ok(playerService.movePlayer(playerId,direction,session.getId()));
    }

    @GetMapping("/unlock/{playerId}")
    public ResponseEntity<Void> unlockPlayer(
            @PathVariable Long playerId, HttpSession session){
        playerService.unlockPlayer(playerId, session.getId());
        return ResponseEntity.ok().build();
    }
}
