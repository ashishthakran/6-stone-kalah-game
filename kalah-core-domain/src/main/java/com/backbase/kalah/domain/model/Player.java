package com.backbase.kalah.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class represent the player of the game, its pit data (start index, end index and Kalah house index).
 *
 * @author Aashish
 * @version 1.0
 */

@Getter
@AllArgsConstructor
public enum Player {

    PLAYER1(1, 1, 6, 7),
    PLAYER2(2, 8, 13, 14)
    ;

    private final Integer playerIndex;
    private final Integer playerPitStartIndex;
    private final Integer playerPitEndIndex;
    private final Integer playerHouseIndex;

    /**
     * This method returns all pit indexes.
     * @return
     */
    @JsonIgnore
    public static List<Integer> getAllPitIndex() {
        return IntStream.rangeClosed(PLAYER1.getPlayerPitStartIndex(), PLAYER2.getPlayerHouseIndex())
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * This method returns all Kalah houses.
     * @return
     */
    @JsonIgnore
    public static List<Integer> getAllHouseIndex() {
        return Arrays.asList(PLAYER1.getPlayerHouseIndex(), PLAYER2.getPlayerHouseIndex());
    }
}
