package com.backbase.kalah.rest.mapper;

import com.backbase.kalah.rest.model.CreateGameResponse;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateGameResponseMapperTest {

    private final CreateGameResponseMapper createGameResponseMapper = new CreateGameResponseMapper();

    @Test
    public void testMapToResponse() {
        //GIVEN
        val url = "http://localhost.me";
        val id = 1;

        //VERIFY
        CreateGameResponse createGameResponse = createGameResponseMapper.mapToResponse(id, url);

        assertThat(createGameResponse).isNotNull()
                .hasFieldOrPropertyWithValue("id", String.valueOf(id))
                .hasFieldOrPropertyWithValue("uri", url);
    }
}
