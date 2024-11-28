package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;


@Entity
@Getter
@Table(name = "runs_leaderboard")
public class LeaderboardRun {
    @Id
    @Column(name = "run_id")
    private Long runId;

    @Column(name = "username")
    private String username;

    @Column(name = "character_id")
    private Integer characterId;

    @Column(name = "player_id")
    private UUID playerId;

    @OneToOne
    @JoinColumn(name = "run_id", referencedColumnName = "id")
    private Run run;
}