package com.backbase.kalah.data.dao;

import com.backbase.kalah.data.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This class is used to save game data in database.
 *
 * @author Aashish
 * @version 1.0
 */

@Repository
public interface GameDao extends JpaRepository<GameEntity, Integer> {
}
