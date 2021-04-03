package com.backbase.kalah.domain.engine.rule;

import com.backbase.kalah.domain.exception.IllegalMoveException;
import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Pit;
import com.backbase.kalah.domain.model.Player;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible to check starting rules for the distributing stones.
 *
 * @author Aashish
 * @version 1.0
 */
@Slf4j
public class GameStartRule extends KalahRule {

    @Override
    public void apply(Game game, Pit startPit) {
        log.debug("check rules for the start pit {} of game {}", startPit.getId(), game.getId());

        checkPlayerTurn(game, startPit);
        checkEmptyPit(startPit);
        this.next.apply(game, startPit);
    }

    private void checkPlayerTurn(Game game, Pit startPit){

        if(game.getGameStatus().equals(GameStatus.INIT)) {
            GameStatus gameStatus =  startPit.getPlayer().getPlayerIndex().equals(Player.PLAYER1.getPlayerIndex()) ? GameStatus.PLAYER1_TURN : GameStatus.PLAYER2_TURN;
            game.setGameStatus(gameStatus);
        }

        if((game.getGameStatus().equals(GameStatus.PLAYER1_TURN) && startPit.getId() >= Player.PLAYER1.getPlayerHouseIndex()) ||
                (game.getGameStatus().equals(GameStatus.PLAYER2_TURN) && startPit.getId() <= Player.PLAYER1.getPlayerHouseIndex())){
            throw new IllegalMoveException("You are not allowed. It's another player's turn.");
        }
    }

    private void checkEmptyPit(Pit startPit){
        if(startPit.getNumberOfStones() == 0){
            throw new IllegalMoveException("Pit is empty.");
        }
    }
}
