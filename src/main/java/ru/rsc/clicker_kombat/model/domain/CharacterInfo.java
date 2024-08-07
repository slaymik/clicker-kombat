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
@Table(name = "character_info")
public class CharacterInfo {
    @Id
    @Column(name = "character_id")
    private Long characterId;

    @OneToOne
    @JoinColumn(name = "character_id", referencedColumnName = "id")
    private Character character;

    @Column(name = "faction_id")
    private Long factionId;

    @Column(name = "faction_name")
    private String factionName;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "faction_id", referencedColumnName = "id")
    private Faction faction;

    @Column(name = "profit")
    private Integer profit;

    @PostLoad
    public void updateFactionNameAndIsActive(){
        if(faction != null){
            factionName = faction.getName();
            isActive = faction.getIsActive();
        }
    }
}