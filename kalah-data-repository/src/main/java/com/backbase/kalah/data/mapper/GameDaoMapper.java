package com.backbase.kalah.data.mapper;

import com.backbase.kalah.data.entity.GameEntity;
import com.backbase.kalah.domain.model.Game;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * This class maps internal domain models and DAO entities.
 *
 * @author Aashish
 * @version 1.0
 */
@Slf4j
@Component
public class GameDaoMapper {

    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

    /**
     * This method maps internal domain Game model to Game entity.
     * @param game
     * @return
     * @throws JsonProcessingException
     */
    public GameEntity mapToEntity(Game game) throws JsonProcessingException {
        return GameEntity.builder()
                .id(game.getId())
                .data(mapper.writeValueAsString(game))
                .build();
    }

    /**
     * This method maps Game entity to internal domain Game model.
     * @param gameEntity
     * @return
     * @throws JsonProcessingException
     */
    public Game mapToDomain(GameEntity gameEntity) throws JsonProcessingException {
        Game game = mapper.readValue(gameEntity.getData(), Game.class);
        return game.toBuilder().id(gameEntity.getId()).build();
    }
}
