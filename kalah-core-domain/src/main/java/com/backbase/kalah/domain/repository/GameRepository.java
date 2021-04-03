package com.backbase.kalah.domain.repository;

import com.backbase.kalah.domain.model.Game;

/**
 * This class represents storage repository.
 *
 * @author Aashish
 * @version 1.0
 */
public interface GameRepository {

    Game create(Game game);

    Game findById(Integer id);

    void update(Game game);
}
