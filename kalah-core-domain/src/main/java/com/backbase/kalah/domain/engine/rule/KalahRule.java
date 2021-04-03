package com.backbase.kalah.domain.engine.rule;

import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.model.Pit;

/**
 * This class defines the game rule which other rules will inherit and make decisions.
 *
 * @author Aashish
 * @version 1.0
 */
public abstract class KalahRule {

    protected KalahRule next;
    public abstract void apply(Game game, Pit currentPit);

    public KalahRule setNext(KalahRule next) {
        this.next = next;
        return next;
    }
}
