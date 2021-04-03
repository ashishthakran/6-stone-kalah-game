package com.backbase.kalah.data.entity;

import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Pit;
import com.backbase.kalah.domain.model.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class GameEntityTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetters() throws JsonProcessingException {
        val gameEntity = createGameEntity();
        val game = createGame();
        val strGame = mapper.writeValueAsString(game);

        assertThat(gameEntity).isNotNull()
        .hasFieldOrPropertyWithValue("id", 1)
        .hasFieldOrPropertyWithValue("data", strGame);
    }

    @Test
    public void testEquals_HashCode_ToString() {
        val gameEntity1 = createGameEntity();
        val gameEntity2 = createGameEntity();

        assertThat(gameEntity1)
                .isEqualTo(gameEntity2)
                .hasSameHashCodeAs(gameEntity2)
                .hasToString(gameEntity2.toString());
    }

    private GameEntity createGameEntity() {
        String data= null;
        try {
            data = mapper.writeValueAsString(createGame());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return GameEntity.builder()
                .id(1)
                .data(data)
                .build();
    }

    private Game createGame() {
        return Game.builder()
                .id(1)
                .player1(Player.PLAYER1)
                .player2(Player.PLAYER2)
                .gameStatus(GameStatus.INIT)
                .pits(Collections.singletonList(Pit.builder().id(1).numberOfStones(6).player(Player.PLAYER1).build()))
                .build();
    }
}
