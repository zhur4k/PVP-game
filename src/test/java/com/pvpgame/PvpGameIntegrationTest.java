package com.pvpgame;

import com.pvpgame.dto.PlayerContextDTO;
import com.pvpgame.dto.PlayerToChooseDTO;
import com.pvpgame.model.Direction;
import com.pvpgame.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PvpGameIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


    private PlayerToChooseDTO[] players;

    @BeforeEach
    public void SetUp() {
        ResponseEntity<PlayerToChooseDTO[]> response = restTemplate.getForEntity("/players", PlayerToChooseDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PlayerToChooseDTO[] players = response.getBody();

        assertNotNull(players, "Failed to load players from InitialDataLoader.");
        this.players = players;

    }

    @Test
    @Transactional
    void selectAndMovePlayerTest() {
        for (PlayerToChooseDTO player : players) {
            Long playerId = player.id();
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<PlayerContextDTO> selectResponse = restTemplate.exchange(
                    "/players/" + playerId + "/select", HttpMethod.GET, new HttpEntity<>(headers), PlayerContextDTO.class);

            assertEquals(HttpStatus.OK, selectResponse.getStatusCode());

            String sessionId = selectResponse.getHeaders().getFirst("Set-Cookie");
            assertNotNull(sessionId, "Session ID should not be null after selecting player.");

            headers.add("Cookie", sessionId);
            PlayerContextDTO playerContextDTO = selectResponse.getBody();

            for (int i = 0; i < 5; i++) {
                if (!playerContextDTO.shouldBattle()) {

                    Direction direction = null;
                    for (Direction tempDirection : Direction.values()){
                        if(tempDirection != null){
                            direction = tempDirection;
                            break;
                        }
                    }

                    if(direction == null){
                        continue;
                    }
                    HttpEntity<Direction> request = new HttpEntity<>(direction, headers);
                    ResponseEntity<PlayerContextDTO> moveResponse = restTemplate.exchange(
                            "/players/move?direction=" + direction.name(),
                            HttpMethod.POST,
                            new HttpEntity<>(headers),
                            PlayerContextDTO.class);

                    assertEquals(HttpStatus.OK, moveResponse.getStatusCode());

                    playerContextDTO = moveResponse.getBody();
                    assertNotNull(playerContextDTO);
                    assertEquals(playerContextDTO.id(), playerId);
                } else {
                    if (i % 2 == 0) {
                        ResponseEntity<String> battleResponse = restTemplate.exchange(
                                "/battle/start",
                                HttpMethod.GET,
                                new HttpEntity<>(headers),
                                String.class);

                        assertEquals(HttpStatus.OK, battleResponse.getStatusCode());
                        assertEquals("Battle started!", battleResponse.getBody());

                        ResponseEntity<String> endResponse = restTemplate.exchange(
                                "/battle/end",
                                HttpMethod.GET,
                                new HttpEntity<>(headers),
                                String.class);

                        assertEquals(HttpStatus.OK, endResponse.getStatusCode());
                        assertEquals("Battle ended!", endResponse.getBody());

                    } else {
                        ResponseEntity<String> apologizeResponse = restTemplate.exchange(
                                "/battle/apologize",
                                HttpMethod.GET,
                                new HttpEntity<>(headers),
                                String.class);

                        assertEquals(HttpStatus.OK, apologizeResponse.getStatusCode());
                        assertEquals("Battle ended! You apologized.", apologizeResponse.getBody());
                    }
                }
                ResponseEntity<PlayerContextDTO> updatedPlayerResponse = restTemplate.exchange(
                        "/player",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        PlayerContextDTO.class);

                playerContextDTO = updatedPlayerResponse.getBody();
                assertNotNull(playerContextDTO);
            }
            ResponseEntity<Void> unlockResponse = restTemplate.exchange(
                    "/players/unlock",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Void.class);

            assertEquals(HttpStatus.OK, unlockResponse.getStatusCode(), "Player should be successfully unlocked.");
        }
    }
}
