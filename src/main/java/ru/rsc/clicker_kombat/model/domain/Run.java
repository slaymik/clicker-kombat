package ru.rsc.clicker_kombat.model.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "character", columnDefinition = "jsonb")
    private Character character;

    @Column(name = "level")
    private Integer level;

    @Column(name = "shit_coins")
    private Long shitCoins;

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

    @Column(name = "is_ended")
    private Boolean isEnded;

    @Column(name = "is_heroic")
    private Boolean isHeroic;
}