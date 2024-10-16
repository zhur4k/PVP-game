package com.pvpgame;

import com.pvpgame.dto.PlayerContextDTO;
import com.pvpgame.dto.PlayerToChooseDTO;
import com.pvpgame.model.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PvpGameIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Long playerId;

    @BeforeEach
    public void SetUp(){
        ResponseEntity<PlayerToChooseDTO[]> response = restTemplate.getForEntity("/players", PlayerToChooseDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PlayerToChooseDTO[] players = response.getBody();

        assertNotNull(players, "Failed to load players from InitialDataLoader.");

        Random random = new Random();

        playerId = players[random.nextInt(players.length)].id();
    }

    @Test
    @Transactional
    void selectAndMovePlayerTest(){
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<PlayerContextDTO> selectResponse = restTemplate.exchange(
                "/players/" + playerId + "/select", HttpMethod.GET, new HttpEntity<>(headers), PlayerContextDTO.class);

        assertEquals(HttpStatus.OK, selectResponse.getStatusCode());

        String sessionId = selectResponse.getHeaders().getFirst("Set-Cookie");
        assertNotNull(sessionId, "Session ID should not be null after selecting player.");

        headers.add("Cookie", sessionId);

        HttpEntity<Direction> request = new HttpEntity<>(Direction.UP, headers);
        ResponseEntity<PlayerContextDTO> moveResponse = restTemplate.exchange(
                "/players/move?direction=UP",
                HttpMethod.POST,
                request,
                PlayerContextDTO.class);

        assertEquals(HttpStatus.OK, moveResponse.getStatusCode());

        PlayerContextDTO movedPlayer = moveResponse.getBody();
        assertNotNull(movedPlayer);
        assertEquals(movedPlayer.id(), playerId);
    }
}
