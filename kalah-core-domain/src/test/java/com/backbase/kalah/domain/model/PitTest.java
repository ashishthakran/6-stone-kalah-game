package com.backbase.kalah.domain.model;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PitTest {

    @Test
    public void testGetters() {
        val pit = createPit();

        assertThat(pit).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("numberOfStones", 6)
                .hasFieldOrPropertyWithValue("player", Player.PLAYER1);
    }

    @Test
    public void testEquals_HashCode_ToString() {
        val pit1 = createPit();
        val pit2 = createPit();

        assertThat(pit1)
                .isEqualTo(pit2)
                .hasSameHashCodeAs(pit2)
                .hasToString(pit2.toString());
    }

    private Pit createPit() {
        return Pit.builder()
                .player(Player.PLAYER1)
                .numberOfStones(6)
                .id(1)
                .build();
    }
}
