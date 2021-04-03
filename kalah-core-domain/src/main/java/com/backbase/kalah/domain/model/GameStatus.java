package com.backbase.kalah.domain.model;

/**
 * Identifies the current game status
 *
 * @author Aashish
 * @version 1.0
 */
public enum GameStatus {

    /**
     * Game was initiated but not started.
     */
    INIT,

    /**
     * Player 1 is on the turn
     */
    PLAYER1_TURN,

    /**
     * Player 2 is on the turn
     */
    PLAYER2_TURN,

    /**
     * Game has finished
     */
    FINISHED
}
