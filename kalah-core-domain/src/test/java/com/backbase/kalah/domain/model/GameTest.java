package com.backbase.kalah.domain.model;

import com.backbase.kalah.domain.exception.InvalidPitIdException;
import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {

    @Test
    public void testGetters() {
        val game = createGame();

        assertThat(game).isNotNull()
                .hasFieldOrPropertyWithValue("id", game.getId())
                .hasFieldOrPropertyWithValue("player1", game.getPlayer1())
                .hasFieldOrPropertyWithValue("player2", game.getPlayer2())
                .hasFieldOrPropertyWithValue("gameStatus", game.getGameStatus())
                .hasFieldOrProperty("pits");
    }

    @Test
    public void testEquals_HashCode_ToString() {
        val game1 = createGame();
        val game2 = createGame();

        assertThat(game1)
                .isEqualTo(game2)
                .hasSameHashCodeAs(game2)
                .hasToString(game2.toString());
    }

    @Test
    @DisplayName("Throw InvalidPitIdException if invalid pit id is passed.")
    public void testGetPit_invalid_pit_id() {
        //GIVEN
        val pitId = 15;
        val game = createGame();

        //WHEN
        Throwable thrownException = assertThrows(InvalidPitIdException.class, () -> game.getPit(pitId));

        //VERIFY
        assertThat(thrownException).hasMessage("Invalid pit id");
    }

    @Test
    public void testGetPit() {
        //GIVEN
        val pitId = 3;
        val game = createGame();

        assertEquals(game.getPit(pitId), Pit.builder().id(3).numberOfStones(6).player(Player.PLAYER1).build());
    }

    private Game createGame() {
        return Game.builder()
                .id(1)
                .player1(Player.PLAYER1)
                .player2(Player.PLAYER2)
                .gameStatus(GameStatus.INIT)
                .pits(createPits())
                .build();
    }

    private List<Pit> createPits() {
        Pit pit1 = Pit.builder().id(1).numberOfStones(6).player(Player.PLAYER1).build();
        Pit pit2 = Pit.builder().id(2).numberOfStones(6).player(Player.PLAYER1).build();
        Pit pit3 = Pit.builder().id(3).numberOfStones(6).player(Player.PLAYER1).build();
        Pit pit4 = Pit.builder().id(4).numberOfStones(6).player(Player.PLAYER1).build();
        Pit pit5 = Pit.builder().id(5).numberOfStones(6).player(Player.PLAYER1).build();
        Pit pit6 = Pit.builder().id(6).numberOfStones(6).player(Player.PLAYER1).build();
        Pit pit7 = Pit.builder().id(7).numberOfStones(0).player(Player.PLAYER1).build();

        Pit pit8 = Pit.builder().id(8).numberOfStones(6).player(Player.PLAYER2).build();
        Pit pit9 = Pit.builder().id(9).numberOfStones(6).player(Player.PLAYER2).build();
        Pit pit10 = Pit.builder().id(10).numberOfStones(6).player(Player.PLAYER2).build();
        Pit pit11 = Pit.builder().id(11).numberOfStones(6).player(Player.PLAYER2).build();
        Pit pit12 = Pit.builder().id(12).numberOfStones(6).player(Player.PLAYER2).build();
        Pit pit13 = Pit.builder().id(13).numberOfStones(6).player(Player.PLAYER2).build();
        Pit pit14 = Pit.builder().id(14).numberOfStones(0).player(Player.PLAYER2).build();

        return Lists.newArrayList(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);
    }
}
