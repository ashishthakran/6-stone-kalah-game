package com.backbase.kalah.domain.service.impl;

import com.backbase.kalah.domain.engine.GameEngine;
import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.Pit;
import com.backbase.kalah.domain.repository.GameRepository;
import com.backbase.kalah.domain.service.GameService;
import com.backbase.kalah.domain.validation.GameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This class acts as a bridge between API and game engine.
 *
 * @author Aashish
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameEngine gameEngine;

    /**
     * This method is responsible to initialize new game.
     *
     * @param numberOfStonesInPit is the initial number of stone.
     * @return
     */
    @Override
    public Game createGame(Integer numberOfStonesInPit) {
        return gameRepository.create(gameEngine.initializeGame(numberOfStonesInPit));
    }

    /**
     * This method is responsible for every new move of the stones from a pit.
     *
     * @param gameId game id
     * @param pitId index of the pit
     * @return Game
     */
    @Override
    public Game makeAMove(Integer gameId, Integer pitId) {
        GameValidator.isValidMove(pitId);

        Game game = gameRepository.findById(gameId);
        GameValidator.checkIfGameIsOver(game.getGameStatus());

        Pit pit = game.getPit(pitId);

        gameEngine.play(game, pit);
        gameRepository.update(game);
        return  game;
    }

}
