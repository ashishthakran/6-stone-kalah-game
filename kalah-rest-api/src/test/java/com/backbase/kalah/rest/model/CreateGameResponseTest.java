package com.backbase.kalah.rest.model;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateGameResponseTest {

    @Test
    public void testGetters() {
        val createResponse = createGameResponse();

        assertThat(createResponse).isNotNull()
                .hasFieldOrPropertyWithValue("id", "1")
                .hasFieldOrPropertyWithValue("uri", "http://localhost.me");
    }

    @Test
    public void testEquals_HashCode_ToString() {
        val createResponse1 = createGameResponse();
        val createResponse2 = createGameResponse();

        assertThat(createResponse1)
                .isEqualTo(createResponse2)
                .hasSameHashCodeAs(createResponse2)
                .hasToString(createResponse2.toString());
    }

    private CreateGameResponse createGameResponse() {
        return CreateGameResponse.builder()
                .id("1")
                .uri("http://localhost.me")
                .build();
    }
}
