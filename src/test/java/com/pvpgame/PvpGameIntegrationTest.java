package com.pvpgame;

import com.pvpgame.dto.PlayerDto;
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
        ResponseEntity<PlayerDto[]> response = restTemplate.getForEntity("/players", PlayerDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PlayerDto[] players = response.getBody();

        assertNotNull(players, "Failed to load players from InitialDataLoader.");

        Random random = new Random();

        PlayerDto randomPlayer = players[random.nextInt(players.length)];

        playerId = randomPlayer.id();
    }

    @Test
    @Transactional
    void selectAndMovePlayerTest(){
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Void> selectResponse = restTemplate.exchange(
                "/players/" + playerId + "/select", HttpMethod.GET, new HttpEntity<>(headers), Void.class);

        assertEquals(HttpStatus.OK, selectResponse.getStatusCode());

        String sessionId = selectResponse.getHeaders().getFirst("Set-Cookie");
        assertNotNull(sessionId, "Session ID should not be null after selecting player.");

        headers.add("Cookie", sessionId);

        HttpEntity<Direction> request = new HttpEntity<>(Direction.UP, headers);
        ResponseEntity<PlayerDto> moveResponse = restTemplate.exchange(
                "/players/" + playerId + "/move?direction=UP",
                HttpMethod.POST,
                request,
                PlayerDto.class);

        assertEquals(HttpStatus.OK, moveResponse.getStatusCode());

        PlayerDto movedPlayer = moveResponse.getBody();
        assertNotNull(movedPlayer);
        assertEquals(movedPlayer.id(), playerId);

        ResponseEntity<PlayerDto[]> playersAtLocationResponse = restTemplate.getForEntity(
                "/locations/" + movedPlayer.locationId() + "/players", PlayerDto[].class);
        assertEquals(HttpStatus.OK, playersAtLocationResponse.getStatusCode());

        PlayerDto[] playersAtLocation = playersAtLocationResponse.getBody();
        assertNotNull(playersAtLocation);
        boolean playerFound = Arrays.stream(playersAtLocation)
                .anyMatch(player -> player.id().equals(playerId));

        assertTrue(playerFound, "Moved player should be present at the new location.");
    }
}
