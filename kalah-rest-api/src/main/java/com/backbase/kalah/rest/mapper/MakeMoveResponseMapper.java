package com.backbase.kalah.rest.mapper;

import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.rest.model.MakeMoveResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * This class is a mapper to map application internal domain model to API model.
 *
 * @author Aashish
 * @version 1.0
 */
@Component
public class MakeMoveResponseMapper {

    public MakeMoveResponse mapToResponse(Game game, String url) {
        return MakeMoveResponse.builder()
                .id(game.getId().toString())
                .url(url)
                .status(game.getPits().stream()
                        .collect(Collectors.toMap(key -> key.getId().toString(),
                                value -> value.getNumberOfStones().toString())))
                .build();
    }
}
