package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
@Table(name = "runs_leaderboard")
public class LeaderboardRun {
    @Id
    @Column(name = "run_id")
    private Long runId;

    @Column(name = "character_id")
    private Integer characterId;

    @OneToOne
    @JoinColumn(name = "run_id", referencedColumnName = "id")
    private Run run;
}