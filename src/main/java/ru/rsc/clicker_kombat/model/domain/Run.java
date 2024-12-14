package ru.rsc.clicker_kombat.model.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "runs")
public class Run {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "character")
    private Integer characterId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "character_params", columnDefinition = "jsonb")
    private JsonNode characterParams;

    @Column(name = "level")
    private Integer level;

    @Column(name = "stage")
    private Integer stage;

    @Column(name = "shit_coins")
    private Integer shitCoins;

    @Column(name = "up_coins")
    private Long runUpCoins;

    @Column(name = "enemy")
    private Integer enemy;

    @Column(name = "world")
    private Integer world;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "boosts", columnDefinition = "jsonb")
    private JsonNode boosts;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "consumables", columnDefinition = "jsonb")
    private JsonNode consumables;

    @Column(name = "start_timestamp")
    private Instant startTime;

    @Column(name = "end_timestamp")
    private Instant endTime;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "is_finished")
    private Boolean isFinished;

    @Column(name = "finished_by_victory")
    private Boolean finishedByVictory;

    @Column(name = "is_heroic")
    private Boolean isHeroic;
}