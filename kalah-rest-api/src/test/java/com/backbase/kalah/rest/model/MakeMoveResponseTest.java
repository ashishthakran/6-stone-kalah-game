package com.backbase.kalah.rest.model;

import lombok.val;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MakeMoveResponseTest {

    @Test
    public void testGetters() {
        val makeMoveResponse = createMakeMoveResponse();

        assertThat(makeMoveResponse).isNotNull()
                .hasFieldOrPropertyWithValue("id", "1")
                .hasFieldOrPropertyWithValue("url", "http://localhost.me")
                .hasFieldOrProperty("status");
    }

    @Test
    public void testEquals_HashCode_ToString() {
        val makeMoveResponse1 = createMakeMoveResponse();
        val makeMoveResponse2 = createMakeMoveResponse();

        assertThat(makeMoveResponse1)
                .isEqualTo(makeMoveResponse2)
                .hasSameHashCodeAs(makeMoveResponse2)
                .hasToString(makeMoveResponse2.toString());
    }

    private MakeMoveResponse createMakeMoveResponse() {
        Map<String, String> status = Maps.newHashMap("1", "2");

        return MakeMoveResponse.builder()
                .id("1")
                .url("http://localhost.me")
                .status(status)
                .build();
    }
}
