package ru.rsc.clicker_kombat.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "character_name")
    private String name;

    @Transient
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;

    public Character(String name, Player player) {
        this.name = name;
        this.player = player;
    }
    @Transient
    public UUID getUserId(){
        return player.getId();
    }
}