package com.backbase.kalah.domain.service;

import com.backbase.kalah.domain.engine.GameEngine;
import com.backbase.kalah.domain.exception.GameOverException;
import com.backbase.kalah.domain.exception.IllegalMoveException;
import com.backbase.kalah.domain.exception.InvalidPitIdException;
import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Pit;
import com.backbase.kalah.domain.model.Player;
import com.backbase.kalah.domain.repository.GameRepository;
import com.backbase.kalah.domain.service.impl.GameServiceImpl;
import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GameServiceImplTest {

    private final GameRepository gameRepository = mock(GameRepository.class);
    private final GameEngine gameEngine = mock(GameEngine.class);
    private final GameService gameService = new GameServiceImpl(gameRepository, gameEngine);

    @Test
    public void testCreateGame() {
        //GIVEN
        val numberOfStones = 6;
        val expectedGame = createGame();

        //WHEN
        when(gameEngine.initializeGame(numberOfStones)).thenReturn(expectedGame);
        when(gameRepository.create(expectedGame)).thenReturn(expectedGame);

        val game = gameService.createGame(numberOfStones);

        //VERIFY
        assertThat(game).isNotNull()
                .hasFieldOrPropertyWithValue("id", expectedGame.getId())
                .hasFieldOrPropertyWithValue("player1", expectedGame.getPlayer1())
                .hasFieldOrPropertyWithValue("player2", expectedGame.getPlayer2())
                .hasFieldOrPropertyWithValue("gameStatus", expectedGame.getGameStatus())
                .hasFieldOrProperty("pits");

        verify(gameEngine, times(1)).initializeGame(numberOfStones);
        verify(gameRepository, times(1)).create(expectedGame);
    }

    @Test
    @DisplayName("Throw InvalidPitIdException when an invalid pit id is given.")
    public void testMakeAMove_invalid_pitId() {
        //GIVEN
        val gameId = 1;
        val pitId = 15;

        //WHEN
        Throwable thrownException = assertThrows(InvalidPitIdException.class, () -> gameService.makeAMove(gameId, pitId));

        //VERIFY
        assertThat(thrownException).hasMessage("Invalid pit id. Pit with 15 doesn't exists.");
    }

    @Test
    @DisplayName("Throw IllegalMoveException when a pit is a house. Move a stone from house is now allowed.")
    public void testMakeAMove_pit_is_house() {
        //GIVEN
        val gameId = 1;
        val pitId = 7;

        //WHEN
        Throwable thrownException = assertThrows(IllegalMoveException.class, () -> gameService.makeAMove(gameId, pitId));

        //VERIFY
        assertThat(thrownException).hasMessage("House stone is not allowed to distribute.");
    }

    @Test
    public void testMakeAMove_when_game_is_over() {
        //GIVEN
        val gameId = 1;
        val pitId = 3;
        val expectedGame = createGame().toBuilder().gameStatus(GameStatus.FINISHED).build();
        Pit pit = expectedGame.getPit(pitId);

        //WHEN
        when(gameRepository.findById(gameId)).thenReturn(expectedGame);
        Throwable thrownException = assertThrows(GameOverException.class, () -> gameService.makeAMove(gameId, pitId));

        //VERIFY
        assertThat(thrownException).hasMessage("Game has been over.");
    }

    @Test
    public void testMakeAMove() {
        //GIVEN
        val gameId = 1;
        val pitId = 3;
        val expectedGame = createGame();
        Pit pit = expectedGame.getPit(pitId);

        //WHEN
        when(gameRepository.findById(gameId)).thenReturn(expectedGame);
        doNothing().when(gameEngine).play(expectedGame, pit);
        doNothing().when(gameRepository).update(expectedGame);

        val game = gameService.makeAMove(gameId, pitId);

        //VERIFY
        assertThat(game).isNotNull()
                .hasFieldOrPropertyWithValue("id", expectedGame.getId())
                .hasFieldOrPropertyWithValue("player1", expectedGame.getPlayer1())
                .hasFieldOrPropertyWithValue("player2", expectedGame.getPlayer2())
                .hasFieldOrPropertyWithValue("gameStatus", expectedGame.getGameStatus())
                .hasFieldOrProperty("pits");

        verify(gameRepository, times(1)).findById(gameId);
        verify(gameEngine, times(1)).play(expectedGame, pit);
        verify(gameRepository, times(1)).update(expectedGame);

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
