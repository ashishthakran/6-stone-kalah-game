package com.backbase.kalah.rest.mapper;

import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Pit;
import com.backbase.kalah.domain.model.Player;
import com.backbase.kalah.rest.model.MakeMoveResponse;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class MakeMoveResponseMapperTest {

    private final MakeMoveResponseMapper makeMoveResponseMapper = new MakeMoveResponseMapper();

    @Test
    public void testMapToResponse() {
        //GIVEN
        val url = "http://localhost.me";
        val game = createGame();

        //VERIFY
        MakeMoveResponse createGameResponse = makeMoveResponseMapper.mapToResponse(game, url);

        assertThat(createGameResponse).isNotNull()
                .hasFieldOrPropertyWithValue("id", String.valueOf(game.getId()))
                .hasFieldOrPropertyWithValue("url", url)
                .hasFieldOrProperty("status");
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
