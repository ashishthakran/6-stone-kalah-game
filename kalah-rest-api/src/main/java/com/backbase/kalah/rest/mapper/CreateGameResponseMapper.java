package com.backbase.kalah.rest.mapper;

import com.backbase.kalah.rest.model.CreateGameResponse;
import org.springframework.stereotype.Component;

/**
 * This class is a mapper to map application internal domain model to API model.
 *
 * @author Aashish
 * @version 1.0
 */
@Component
public class CreateGameResponseMapper {

    public CreateGameResponse mapToResponse(Integer id, String url) {
        return CreateGameResponse.builder()
                .id(id.toString())
                .uri(url)
                .build();
    }
}
