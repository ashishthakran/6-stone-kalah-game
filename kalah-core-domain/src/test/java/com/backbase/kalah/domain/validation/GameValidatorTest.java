package com.backbase.kalah.domain.validation;

import com.backbase.kalah.domain.exception.GameOverException;
import com.backbase.kalah.domain.exception.IllegalMoveException;
import com.backbase.kalah.domain.exception.InvalidPitIdException;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Player;

public abstract class GameValidatorTest {

    public static void checkIfGameIsOver(GameStatus gameStatus) {
        if(GameStatus.FINISHED.equals(gameStatus)) {
            throw new GameOverException("Game has been over.");
        }
    }

    public static void isValidMove(Integer pitId) {
        if(!Player.getAllPitIndex().contains(pitId)){
            throw new InvalidPitIdException(String.format("Invalid pit id. Pit with %d doesn't exists.", pitId));
        }

        if(Player.getAllHouseIndex().contains(pitId)) {
            throw new IllegalMoveException("House stone is not allowed to distribute.");
        }
    }
}
