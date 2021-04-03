package com.backbase.kalah.domain.engine;

import com.backbase.kalah.domain.engine.impl.GameEngineImpl;
import com.backbase.kalah.domain.engine.rule.DistributeStoneRule;
import com.backbase.kalah.domain.engine.rule.GameEndRule;
import com.backbase.kalah.domain.engine.rule.GameOverRule;
import com.backbase.kalah.domain.engine.rule.GameStartRule;
import com.backbase.kalah.domain.engine.rule.KalahRule;
import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Pit;
import com.backbase.kalah.domain.model.Player;
import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GameEngineImplTest {

    private final KalahRule chain;
    private final GameEngine gameEngine = new GameEngineImpl();

    public GameEngineImplTest() {

        this.chain = new GameStartRule();
        chain.setNext(new DistributeStoneRule())
                .setNext(new GameEndRule())
                .setNext(new GameOverRule());
    }

    @Test
    public void testInitializeGame() {
        //GIVEN
        val numberOfStones = 6;
        val expectedGame = createGame();

        val game = gameEngine.initializeGame(numberOfStones);

        //VERIFY
        assertThat(game).isNotNull()
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("player1", expectedGame.getPlayer1())
                .hasFieldOrPropertyWithValue("player2", expectedGame.getPlayer2())
                .hasFieldOrPropertyWithValue("gameStatus", expectedGame.getGameStatus())
                .hasFieldOrProperty("pits");
    }

    @Test
    public void testPlay() {
        //GIVEN
        val expectedGame = createGame();
        val game = createGame();
        Pit pit = Pit.builder().id(3).numberOfStones(6).player(Player.PLAYER1).build();

        gameEngine.play(game, pit);

        Pit nextPit = game.getNextPit(pit);

        assertAll(
                () -> assertThat(game).isNotNull()
                        .hasFieldOrPropertyWithValue("id", expectedGame.getId())
                        .hasFieldOrPropertyWithValue("player1", expectedGame.getPlayer1())
                        .hasFieldOrPropertyWithValue("player2", expectedGame.getPlayer2())
                        .hasFieldOrPropertyWithValue("gameStatus", GameStatus.PLAYER2_TURN),
                () -> assertThat(nextPit).isNotNull()
                        .hasFieldOrPropertyWithValue("id", 4)
                        .hasFieldOrPropertyWithValue("numberOfStones", 7)
                        .hasFieldOrPropertyWithValue("player", Player.PLAYER1)
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

}
