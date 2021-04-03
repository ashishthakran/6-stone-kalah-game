package com.backbase.kalah.data.mapper;

import com.backbase.kalah.data.entity.GameEntity;
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

public class GameDaoMapperTest {

    private final GameDaoMapper gameDaoMapper = new GameDaoMapper();
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testMapToEntity() throws JsonProcessingException {
        //GIVEN
        val game = createGame();
        val expectedGameEntity = createGameEntity();

        val actualGameEntity = gameDaoMapper.mapToEntity(game);

        //VERIFY
        assertThat(actualGameEntity).isNotNull()
                .hasFieldOrPropertyWithValue("id", expectedGameEntity.getId())
                .hasFieldOrPropertyWithValue("data", expectedGameEntity.getData())
                .hasToString(expectedGameEntity.toString());
    }

    @Test
    public void testMapToDomain() throws JsonProcessingException {
        //GIVEN
        val gameEntity = createGameEntity();
        val expectedGame = createGame();


        val actualGame = gameDaoMapper.mapToDomain(gameEntity);

        //VERIFY
        assertThat(actualGame).isNotNull()
                .hasFieldOrPropertyWithValue("id", expectedGame.getId())
                .hasFieldOrPropertyWithValue("player1", expectedGame.getPlayer1())
                .hasFieldOrPropertyWithValue("player2", expectedGame.getPlayer2())
                .hasToString(expectedGame.toString());
    }

    private GameEntity createGameEntity() {
        String data = null;
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
