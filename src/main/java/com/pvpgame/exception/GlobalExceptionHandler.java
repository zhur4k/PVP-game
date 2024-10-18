package com.pvpgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BattleRequiredException.class)
    public ResponseEntity<String> handlerBattleRequired(BattleRequiredException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> handlerPlayerNotFound(PlayerNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found: " + ex.getMessage());
    }

    @ExceptionHandler(PlayerAlreadySelectedByUserException.class)
    public ResponseEntity<String> handlerPlayerAlreadySelectedByUser(PlayerAlreadySelectedByUserException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PlayerAlreadySelectedException.class)
    public ResponseEntity<String> handlerPlayerAlreadySelected(PlayerAlreadySelectedException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<String> handlerLocationNotFound(LocationNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Location not found: " + ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNoContentException(NoContentException ex){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content: " + ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
