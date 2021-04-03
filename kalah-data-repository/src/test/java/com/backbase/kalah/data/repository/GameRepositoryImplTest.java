package com.backbase.kalah.data.repository;

import com.backbase.kalah.data.dao.GameDao;
import com.backbase.kalah.data.entity.GameEntity;
import com.backbase.kalah.data.mapper.GameDaoMapper;
import com.backbase.kalah.domain.exception.GameNotFoundException;
import com.backbase.kalah.domain.exception.KalahException;
import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Pit;
import com.backbase.kalah.domain.model.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GameRepositoryImplTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private final GameDao gameDao = mock(GameDao.class);
    private final GameDaoMapper gameDaoMapper = mock(GameDaoMapper.class);

    private final GameRepositoryImpl gameRepository = new GameRepositoryImpl(gameDao, gameDaoMapper);

    @Test
    @DisplayName("Throw JsonProcessingException if mapping failed between entity and domain")
    public void testCreateNewGame_with_failed_mapping() throws JsonProcessingException {
        //GIVEN
        val game = createGame();
        val gameEntity = createGameEntity();

        //WHEN
        when(gameDao.save(gameEntity)).thenReturn(gameEntity);
        when(gameDaoMapper.mapToEntity(game)).thenThrow(new KalahException("Error in creating game."));
        when(gameDaoMapper.mapToDomain(gameEntity)).thenReturn(game);

        //VERIFY
        Throwable thrownException = assertThrows(KalahException.class, () -> gameRepository.create(game));

        assertThat(thrownException).hasMessage("Error in creating game.");
    }

    @Test
    @DisplayName("Throw GameNotFoundException if game with id is not found")
    public void testGameNotFound() throws JsonProcessingException {
        //GIVEN
        val gameId = 1;

        //WHEN
        when(gameDao.findById(gameId)).thenThrow(new GameNotFoundException("Game not found."));

        //VERIFY
        Throwable thrownException = assertThrows(GameNotFoundException.class, () -> gameRepository.findById(gameId));

        assertThat(thrownException).hasMessage("Game not found.");
    }

    @Test
    public void testCreateNewGame() throws JsonProcessingException {
        //GIVEN
        val game = createGame();
        val gameEntity = createGameEntity();

        //WHEN
        when(gameDao.save(gameEntity)).thenReturn(gameEntity);
        when(gameDaoMapper.mapToEntity(game)).thenReturn(gameEntity);
        when(gameDaoMapper.mapToDomain(gameEntity)).thenReturn(game);

        //VERIFY
        val actualGame = gameRepository.create(game);

        assertThat(actualGame).isNotNull()
                .hasFieldOrPropertyWithValue("id", game.getId())
                .hasFieldOrPropertyWithValue("player1", game.getPlayer1())
                .hasFieldOrPropertyWithValue("player2", game.getPlayer2())
                .hasFieldOrPropertyWithValue("gameStatus", game.getGameStatus());

        verify(gameDao, times(1)).save(any());
        verify(gameDaoMapper, times(1)).mapToEntity(any());
        verify(gameDaoMapper, times(1)).mapToDomain(any());
    }

    @Test
    public void testGetGameDetails() throws JsonProcessingException {
        //GIVEN
        val gameId = 1;
        val game = createGame();
        val gameEntity = createGameEntity();

        //WHEN
        when(gameDao.findById(gameId)).thenReturn(Optional.of(gameEntity));
        when(gameDaoMapper.mapToDomain(gameEntity)).thenReturn(game);

        //VERIFY
        val actualGame = gameRepository.findById(gameId);

        assertThat(actualGame).isNotNull()
                .hasFieldOrPropertyWithValue("id", game.getId())
                .hasFieldOrPropertyWithValue("player1", game.getPlayer1())
                .hasFieldOrPropertyWithValue("player2", game.getPlayer2())
                .hasFieldOrPropertyWithValue("gameStatus", game.getGameStatus());

        verify(gameDao, times(1)).findById(any());
        verify(gameDaoMapper, times(1)).mapToDomain(any());
    }

    @Test
    public void testUpdateGame() throws JsonProcessingException {
        //GIVEN
        val gameId = 1;
        val game = createGame();
        val gameEntity = createGameEntity();

        //WHEN
        when(gameDao.findById(gameId)).thenReturn(Optional.of(gameEntity));
        when(gameDao.save(gameEntity)).thenReturn(gameEntity);
        when(gameDaoMapper.mapToEntity(game)).thenReturn(gameEntity);

        //VERIFY
        gameRepository.update(game);

        verify(gameDao, times(1)).findById(any());
        verify(gameDaoMapper, times(1)).mapToEntity(any());
        verify(gameDao, times(1)).save(any());
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
