package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "character_xp")
public class CharacterXp {
    @Id
    @Column(name = "character_id")
    private Long characterId;

    @OneToOne
    @JoinColumn(name = "character_id", referencedColumnName = "id")
    private Character character;

    @Column(name = "current_xp")
    private Long currentXp;

    @Column(name = "all_xp")
    private Long allXp;

    @Column(name = "xp_requirement")
    private Long xpRequirement;

    @Column(name = "xp_until_next_level")
    private Long needXp;
}