package com.backbase.kalah.domain.engine.rule;

import com.backbase.kalah.domain.exception.KalahException;
import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.GameStatus;
import com.backbase.kalah.domain.model.Pit;
import com.backbase.kalah.domain.model.Player;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible to check whether the game is finished or not.
 *
 * @author Aashish
 * @version 1.0
 */
@Slf4j
public class GameOverRule extends KalahRule {

    @Override
    public void apply(Game game, Pit currentPit) {
        log.debug("checking whether the game {} is finished or not after the selected pit move.", game.getId());

        Integer player1StoneCount = game.getPits().stream()
                .filter(pit -> pit.getId() >= Player.PLAYER1.getPlayerPitStartIndex() && pit.getId() <= Player.PLAYER1.getPlayerPitEndIndex())
                .map(Pit::getNumberOfStones)
                .reduce(0, Integer::sum);

        Integer player2StoneCount = game.getPits().stream()
                .filter(pit -> pit.getId() >= Player.PLAYER2.getPlayerPitStartIndex() && pit.getId() <= Player.PLAYER2.getPlayerPitEndIndex())
                .map(Pit::getNumberOfStones)
                .reduce(0, Integer::sum);

        if( player1StoneCount == 0 || player2StoneCount == 0){
            game.setGameStatus(GameStatus.FINISHED);
            Pit house1 = game.getPits().stream()
                    .filter(pit -> pit.getId().equals(Player.PLAYER1.getPlayerHouseIndex()))
                    .findAny().orElseThrow(() -> new KalahException(String.format("Invalid pit id. Player %d house is missing.", Player.PLAYER1.getPlayerHouseIndex())));

            Pit house2 = game.getPits().stream()
                    .filter(pit -> pit.getId().equals(Player.PLAYER2.getPlayerHouseIndex()))
                    .findAny().orElseThrow(() -> new KalahException(String.format("Invalid pit id. Player %d house is missing.", Player.PLAYER2.getPlayerHouseIndex())));

            house1.setNumberOfStones(house1.getNumberOfStones() + player1StoneCount);
            house2.setNumberOfStones(house2.getNumberOfStones() + player2StoneCount);
        }
    }
}
