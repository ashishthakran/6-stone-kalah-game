package com.backbase.kalah.domain.engine;

import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.Pit;

/**
 * This class represents game actions that engine defines for the game.
 *
 * @author Aashish
 * @version 1.0
 */
public interface GameEngine {

    Game initializeGame(Integer numberOfStonesInPit);

    void play(Game game, Pit pit);
}
