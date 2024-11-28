package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
@Table(name = "players_rating")
public class PlayerRating {

    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "username")
    private String username;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "rank")
    private Long rank;
}