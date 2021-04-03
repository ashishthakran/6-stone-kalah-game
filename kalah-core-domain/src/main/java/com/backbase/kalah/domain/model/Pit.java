package com.backbase.kalah.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class represent the pit in the game.
 *
 * @author Aashish
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Pit {

    private Integer id;
    private Integer numberOfStones;
    private Player player;

    /**
     * This method determine that if this pit is a Kalah House.
     * @return boolean
     */
    @JsonIgnore
    public Boolean isHouse(){
        return this.id.equals(player.getPlayerHouseIndex());
    }
}
