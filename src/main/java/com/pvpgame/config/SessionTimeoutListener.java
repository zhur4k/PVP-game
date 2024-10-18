package com.pvpgame.config;

import com.pvpgame.service.PlayerService;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionTimeoutListener implements HttpSessionListener {

    private final PlayerService playerService;

    @Override
    public void sessionDestroyed(HttpSessionEvent event){
        String sessionId = event.getSession().getId();

        playerService.releasePlayersBySession(sessionId);
    }
}
