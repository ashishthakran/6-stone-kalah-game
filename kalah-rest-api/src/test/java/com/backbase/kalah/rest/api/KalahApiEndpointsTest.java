package com.backbase.kalah.rest.api;

import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Pit;
import com.backbase.kalah.domain.model.Player;
import com.backbase.kalah.domain.service.GameService;
import com.backbase.kalah.rest.constants.Constants;
import com.backbase.kalah.rest.mapper.CreateGameResponseMapper;
import com.backbase.kalah.rest.mapper.MakeMoveResponseMapper;
import com.backbase.kalah.rest.model.CreateGameResponse;
import com.backbase.kalah.rest.model.MakeMoveResponse;
import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KalahApiEndpointsTest {

    private final GameService gameService = mock(GameService.class);
    private final CreateGameResponseMapper createGameResponseMapper = mock(CreateGameResponseMapper.class);
    private final MakeMoveResponseMapper makeMoveResponseMapper = mock(MakeMoveResponseMapper.class);
    private final KalahApiEndpoints kalahApiEndpoints = new KalahApiEndpoints(gameService, createGameResponseMapper, makeMoveResponseMapper);


    @Test
    public void testCreateGame() {

        //GIVEN
        val numberOfStones = 6;
        val game = createGame();
        val mockRequest = createMockRequest();
        val url = getUrl(mockRequest, game.getId());
        val createGameResponse = createGameResponse(game.getId(), url);

        //WHEN
        when(gameService.createGame(numberOfStones)).thenReturn(game);
        when(createGameResponseMapper.mapToResponse(game.getId(), url)).thenReturn(createGameResponse);

        //VERIFY
        val response = kalahApiEndpoints.createGame(mockRequest, numberOfStones);

        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertTrue(response.hasBody()),
                () -> assertThat(response.getBody())
                        .hasFieldOrPropertyWithValue("id", game.getId().toString())
                        .hasFieldOrPropertyWithValue("uri", url)
        );
    }

    @Test
    public void testMakeAMove() {

        //GIVEN
        val gameId = 1;
        val pitId = 3;

        val game = createGame();
        val mockRequest = createMockRequest();
        val url = getUrl(mockRequest, game.getId());
        val makeMoveResponse = createMakeMoveResponse(game, url);


        //WHEN
        when(gameService.makeAMove(gameId, pitId)).thenReturn(game);
        when(makeMoveResponseMapper.mapToResponse(game, url)).thenReturn(makeMoveResponse);

        //VERIFY
        val response = kalahApiEndpoints.makeAMove(mockRequest, gameId, pitId);

        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertTrue(response.hasBody()),
                () -> assertThat(response.getBody())
                        .hasFieldOrPropertyWithValue("id", game.getId().toString())
                        .hasFieldOrPropertyWithValue("url", url)
        );
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

    private CreateGameResponse createGameResponse(Integer id, String url) {
        return CreateGameResponse.builder()
                .id(id.toString())
                .uri(url)
                .build();
    }

    private MakeMoveResponse createMakeMoveResponse(Game game, String url) {
        return MakeMoveResponse.builder()
                .id(game.getId().toString())
                .url(url)
                .status(game.getPits().stream()
                        .collect(Collectors.toMap(key -> key.getId().toString(),
                                value -> value.getNumberOfStones().toString())))
                .build();
    }

    private MockHttpServletRequest createMockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost");
        request.setLocalPort(8080);
        request.setRemotePort(8080);
        request.setScheme("http");
        return request;
    }

    private String getUrl(MockHttpServletRequest request, Integer gameId) {
        return MessageFormat.format(Constants.GAME_URL,
                request.getScheme(), request.getServerName(), String.valueOf(request.getServerPort()), gameId);
    }

}
