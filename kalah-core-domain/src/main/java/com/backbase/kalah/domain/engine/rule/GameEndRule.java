package com.backbase.kalah.domain.engine.rule;

import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Pit;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible to check the last stone placing rules.
 *
 * @author Aashish
 * @version 1.0
 */
@Slf4j
public class GameEndRule extends KalahRule {

    @Override
    public void apply(Game game, Pit endPit) {
        log.debug("checking end rule for the last pit {}", endPit);

        lastEmptyPitRule(game, endPit);
        nextPlayerTurnRule(game, endPit);
        this.next.apply(game, endPit);
    }

    private void lastEmptyPitRule(Game game, Pit endPit){
        if (!endPit.isHouse() && game.isPlayerPit(game.getGameStatus(), endPit.getId()) && endPit.getNumberOfStones().equals(1) ) {
            Pit oppositePit = game.getOppositePit(endPit);
            if (oppositePit.getNumberOfStones() > 0) {
                Pit house = game.getPlayerHouse(endPit);
                house.setNumberOfStones((house.getNumberOfStones() + oppositePit.getNumberOfStones()) + endPit.getNumberOfStones());
                oppositePit.setNumberOfStones(0);
                endPit.setNumberOfStones(0);
            }
        }
    }

    private void nextPlayerTurnRule(Game game, Pit endPit){
        if(endPit.isHouse() && game.getGameStatus().equals(GameStatus.PLAYER1_TURN)) {
            game.setGameStatus(GameStatus.PLAYER1_TURN);
        } else if(endPit.isHouse() && game.getGameStatus().equals(GameStatus.PLAYER2_TURN)) {
            game.setGameStatus(GameStatus.PLAYER2_TURN);
        } else {
            GameStatus changeStage = game.getGameStatus() == GameStatus.PLAYER1_TURN ? GameStatus.PLAYER2_TURN : GameStatus.PLAYER1_TURN;
            game.setGameStatus(changeStage);
        }
    }
}
