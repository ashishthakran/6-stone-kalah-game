package com.backbase.kalah.domain.exception;

public class GameOverException extends IllegalMoveException {
    public GameOverException(String message) {
        super(message);
    }
}
