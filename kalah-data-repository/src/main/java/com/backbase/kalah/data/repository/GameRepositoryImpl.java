package com.backbase.kalah.data.repository;

import com.backbase.kalah.data.dao.GameDao;
import com.backbase.kalah.data.entity.GameEntity;
import com.backbase.kalah.data.mapper.GameDaoMapper;
import com.backbase.kalah.domain.exception.GameNotFoundException;
import com.backbase.kalah.domain.exception.KalahException;
import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.repository.GameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * This class is the implementation of core domain repository so decouple data storage and core data processing.
 *
 * @author Aashish
 * @version 1.0
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {

    private final GameDao gameDao;
    private final GameDaoMapper gameDaoMapper;

    /**
     * This method creates and save new game in database.
     * @param game
     * @return
     */
    @Override
    @Transactional
    public Game create(Game game) {
        try {
            GameEntity gameEntity = gameDaoMapper.mapToEntity(game);
            gameDao.save(gameEntity);
            game = gameDaoMapper.mapToDomain(gameEntity);
        } catch (JsonProcessingException ex) {
            log.error("Error in creating game.", ex);
            throw new KalahException("Error in creating game.");
        }
        return game;
    }

    /**
     * This method gets game details from database.
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Game findById(Integer id) {
        Game game;
        GameEntity gameEntity = gameDao.findById(id).orElseThrow(() -> new GameNotFoundException("Game not found."));
        try {
            game = gameDaoMapper.mapToDomain(gameEntity);
        } catch (JsonProcessingException ex) {
            log.error("Error in getting game details.", ex);
            throw new KalahException("Error in getting game details.");
        }
        return game;
    }

    /**
     * This method update game data in database.
     * @param game
     */
    @Override
    @Transactional
    public void update(Game game) {
        gameDao.findById(game.getId()).orElseThrow(() -> new GameNotFoundException("Game not found."));
        try {
            GameEntity gameEntity = gameDaoMapper.mapToEntity(game);
            gameDao.save(gameEntity);
        } catch (JsonProcessingException ex) {
            log.error("Error in getting game details.", ex);
            throw new KalahException("Error in getting game details.");
        }
    }
}
