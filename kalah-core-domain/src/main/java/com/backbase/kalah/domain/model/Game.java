package com.backbase.kalah.domain.model;

import com.backbase.kalah.domain.exception.InvalidPitIdException;
import com.backbase.kalah.domain.exception.KalahException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;


/**
 * This class represent the game. A game contain players, games status and pits.
 *
 * @author Aashish
 * @version 1.0
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Game{

    @JsonIgnore
    private Integer id;
    private Player player1;
    private Player player2;
    private GameStatus gameStatus;
    private List<Pit> pits;

    /**
     * This method gives the selected pit from game for the provided pit id.
     * @param pitId
     * @return
     */
    @JsonIgnore
    public Pit getPit(Integer pitId) {
        return pits.stream()
                .filter(pit -> pit.getId().equals(pitId))
                .findAny()
                .orElseThrow(() -> new InvalidPitIdException("Invalid pit id"));
    }

    /**
     * This method determines whether its player pit or Kalah house.
     * @param gameStatus
     * @param pitId
     * @return Boolean false if player1 with house2 Or player 2 with house1, otherwise true
     */
    @JsonIgnore
    public Boolean isNotDistributable(GameStatus gameStatus, Integer pitId){

        return (gameStatus.equals(GameStatus.PLAYER1_TURN) && pitId.equals(this.getPlayer2().getPlayerHouseIndex()))
                || (gameStatus.equals(GameStatus.PLAYER2_TURN) && pitId.equals(this.getPlayer1().getPlayerHouseIndex()));
    }

    /**
     * This method is use to determine the ownership of current player.
     * Player1 owns pit index from 1-7. Player 2 owns pit index from 8-14.
     *
     * @param gameStatus current game state. In this case player turn
     * @return True if current player is the owner of this pit otherwise false.
     */
    @JsonIgnore
    public Boolean isPlayerPit(GameStatus gameStatus, Integer pitId){

        if(gameStatus.equals(GameStatus.PLAYER1_TURN) && (pitId >= Player.PLAYER1.getPlayerPitStartIndex() && pitId <= Player.PLAYER1.getPlayerHouseIndex()) ) {
            return true;
        }else if(gameStatus.equals(GameStatus.PLAYER2_TURN) && (pitId >= Player.PLAYER2.getPlayerPitStartIndex() && pitId <= Player.PLAYER2.getPlayerHouseIndex()) ) {
            return true;
        }

        return false;
    }

    /**
     * This method gets player Kalah house.
     * Player1 Kalah house pit index is 7. Player 2 Kalah house pit index is 14..
     *
     * @param pit
     * @return Pit
     */
    @JsonIgnore
    public Pit getPlayerHouse(Pit pit){
        return this.getPits().stream()
                .filter(p -> p.getPlayer().getPlayerHouseIndex().equals(pit.getPlayer().getPlayerHouseIndex()))
                .findAny().orElseThrow(() -> new KalahException(String.format("Invalid pit id. Player %d house is missing.", pit.getPlayer().getPlayerIndex())));
    }

    /**
     * This method returns next pit of the selected pit.
     * @param pit
     * @return
     */
    @JsonIgnore
    public Pit getNextPit(Pit pit){
        final Integer nextPitId = pit.getId() + 1 > pits.size() ? 1 : pit.getId() + 1;
        return pits.stream()
                .filter(p -> p.getId().equals(nextPitId))
                .findAny()
                .orElseThrow(() -> new KalahException("Invalid pit id. Unable to find next pit."));
    }

    /**
     * This method returns the opposite pit of the selected pit.
     * @param pit
     * @return
     */
    @JsonIgnore
    public Pit getOppositePit(Pit pit){
        Integer oppositePitId = (Player.PLAYER1.getPlayerPitStartIndex() + Player.PLAYER2.getPlayerHouseIndex() - 1) - pit.getId();
        return pits.stream()
                .filter(p -> p.getId().equals(oppositePitId))
                .findAny().orElseThrow(() -> new KalahException("Invalid pit id. Unable to find opposite pit."));
    }

    /**
     * This method determines the game winner.
     * @return
     */
    @JsonIgnore
    public Player getWinner() {
        Pit player1House = pits.stream()
                .filter(p -> p.getId().equals(Player.PLAYER1.getPlayerHouseIndex()))
                .findAny().orElseThrow(() -> new KalahException(String.format("Invalid pit id. Player %d house is missing.", Player.PLAYER1.getPlayerHouseIndex())));
        Pit player2House = pits.stream()
                .filter(p -> p.getId().equals(Player.PLAYER2.getPlayerHouseIndex()))
                .findAny().orElseThrow(() -> new KalahException(String.format("Invalid pit id. Player %d house is missing.", Player.PLAYER2.getPlayerHouseIndex())));

        if(player1House.getNumberOfStones() > player2House.getNumberOfStones()) {
            return Player.PLAYER1;
        } else if(player1House.getNumberOfStones() < player2House.getNumberOfStones()) {
            return Player.PLAYER2;
        }

        return null;
    }
}
