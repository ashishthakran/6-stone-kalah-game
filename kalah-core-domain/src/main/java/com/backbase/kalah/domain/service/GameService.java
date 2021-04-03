package com.backbase.kalah.domain.service;

import com.backbase.kalah.domain.model.Game;

/**
 * This class acts as a bridge between API and game engine.
 *
 * @author Aashish
 * @version 1.0
 */
public interface GameService {

    Game createGame(Integer numberOfStonesInPit);

    Game makeAMove(Integer gameId, Integer pitId);
}
