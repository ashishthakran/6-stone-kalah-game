package com.backbase.kalah.domain.engine.impl;

import com.backbase.kalah.domain.engine.GameEngine;
import com.backbase.kalah.domain.engine.rule.DistributeStoneRule;
import com.backbase.kalah.domain.engine.rule.GameEndRule;
import com.backbase.kalah.domain.engine.rule.GameOverRule;
import com.backbase.kalah.domain.engine.rule.GameStartRule;
import com.backbase.kalah.domain.engine.rule.KalahRule;
import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Pit;
import com.backbase.kalah.domain.model.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.backbase.kalah.domain.model.Player.PLAYER1;
import static com.backbase.kalah.domain.model.Player.PLAYER2;

/**
 * This class contains game rules.
 *
 * @author Aashish
 * @version 1.0
 */
@Component
public class GameEngineImpl implements GameEngine {

    private final KalahRule chain;

    public GameEngineImpl() {
        this.chain = new GameStartRule();
        chain.setNext(new DistributeStoneRule())
                .setNext(new GameEndRule())
                .setNext(new GameOverRule());
    }

    /**
     * This method take number of stones that will be put in each pit, create and initialize new game.
     * @param numberOfStonesInPit
     * @return
     */
    @Override
    public Game initializeGame(Integer numberOfStonesInPit) {
        Game game = Game.builder()
                .gameStatus(GameStatus.INIT)
                .player1(PLAYER1)
                .player2(PLAYER2)
                .pits(initializePlayerPits(numberOfStonesInPit, Arrays.asList(PLAYER1, PLAYER2)))
                .build();
        return game;
    }

    /**
     * This method takes the game and the pit for which stones need to be moved.
     * <p>
     *     A set of chained game rules are applied to move the selected pit.
     * </p>
     * @param game
     * @param pit
     */
    @Override
    public void play(Game game, Pit pit) {
        this.chain.apply(game, pit);
    }

    /**
     * This method creates and initializes pits for the game.
     * @param numberOfStonesInPit
     * @param players
     * @return
     */
    private List<Pit> initializePlayerPits(Integer numberOfStonesInPit, List<Player> players) {
        List<Pit> pits = new ArrayList<>();
        players.forEach(player -> {
            pits.addAll(IntStream.rangeClosed(player.getPlayerPitStartIndex(), player.getPlayerHouseIndex())
                    .mapToObj(index -> Pit.builder()
                            .numberOfStones(player.getPlayerHouseIndex().equals(index) ? 0 : numberOfStonesInPit)
                            .id(index)
                            .player(player)
                            .build())
                    .collect(Collectors.toList()));
        });
        return pits;
    }

}
