package ru.rsc.clicker_kombat.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "character_game_id")
    private Integer characterGameId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Setter
    @Column(name = "items", columnDefinition = "jsonb")
    private JsonNode items;

    @OneToOne(mappedBy = "character")
    private CharacterXp characterXp;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;
}