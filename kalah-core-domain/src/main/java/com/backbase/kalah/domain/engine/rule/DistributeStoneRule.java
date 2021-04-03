package com.backbase.kalah.domain.engine.rule;

import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.Pit;
import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the rule which distributes stones to the next pits except for the opponent house.
 *
 * @author Aashish
 * @version 1.0
 */
@Slf4j
public class DistributeStoneRule extends KalahRule {

    @Override
    public void apply(Game game, Pit currentPit) {
        log.debug("check the rules for distributing stone to the next pit(s)");

        Integer stoneToDistribute = currentPit.getNumberOfStones();
        currentPit.setNumberOfStones(0);

        int index = 0;
        while (index < stoneToDistribute) {
            currentPit = game.getNextPit(currentPit);
            log.debug("next pit is {}", currentPit.getId());
            /**
             * It checks whether to put stone in the pit. A stone will not be put if
             * next pit is opponent's house.
             */
            if (game.isNotDistributable(game.getGameStatus(), currentPit.getId()))
                continue;

            currentPit.setNumberOfStones(currentPit.getNumberOfStones() + 1);
            index++;
        }

        this.next.apply(game, currentPit);
    }
}
