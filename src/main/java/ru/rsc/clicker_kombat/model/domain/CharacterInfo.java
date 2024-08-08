package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "character_info")
public class CharacterInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @Column(name = "faction_name")
    private String factionName;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "faction_id")
    private Faction faction;

    @Setter
    @Column(name = "profit")
    private Integer profit;

    @PostPersist
    public void updateFactionNameAndIsActive(){
        if(faction != null){
            factionName = faction.getName();
            isActive = faction.getIsActive();
        }
    }
}