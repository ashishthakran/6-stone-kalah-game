package com.backbase.kalah.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Entity class that maps to database table.
 *
 * @author Aashish
 * @version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "game")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Lob
    @Column(name = "game_data", nullable = false)
    private String data;
}
