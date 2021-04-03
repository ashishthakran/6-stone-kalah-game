package com.backbase.kalah.domain.validation;

import com.backbase.kalah.domain.exception.GameOverException;
import com.backbase.kalah.domain.exception.IllegalMoveException;
import com.backbase.kalah.domain.exception.InvalidPitIdException;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Player;

/**
 * This class contains basic game validations like valid pit id, if game is over etc.
 *
 * @author Aashish
 * @version 1.0
 */
public abstract class GameValidator {

    /**
     * This method checks game status and throw GameOverException if game has already been finished.
     * @param gameStatus
     */
    public static void checkIfGameIsOver(GameStatus gameStatus) {
        if(GameStatus.FINISHED.equals(gameStatus)) {
            throw new GameOverException("Game has been over.");
        }
    }

    /**
     * This method checks whether a pit is a valid pit or a Kalah house and throw an exception if
     * it's an invalid pit or a Kalah house.
     * @param pitId
     */
    public static void isValidMove(Integer pitId) {
        if(!Player.getAllPitIndex().contains(pitId)){
            throw new InvalidPitIdException(String.format("Invalid pit id. Pit with %d doesn't exists.", pitId));
        }

        if(Player.getAllHouseIndex().contains(pitId)) {
            throw new IllegalMoveException("House stone is not allowed to distribute.");
        }
    }
}
