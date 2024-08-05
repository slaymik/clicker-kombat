package ru.rsc.clicker_kombat.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ru.rsc.clicker_kombat.utils.converters.UpgradesConverter;

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

    @Column(name = "faction")
    private String faction;

    @Column(name = "level")
    private Short level;

    @Column(name = "up_coins")
    private Long upCoins;

    @Column(name = "current_xp")
    private Long currentXp;

    @Column(name = "all_xp")
    private Long allXp;

    @Column(name = "xp_requirement")
    private Long xpRequirement;

    @Column(name = "xp_until_next_level")
    private Long needXp;

    @Transient
    private Long userId;

    @Column(name = "upgrades")
    @Convert(converter = UpgradesConverter.class)
    private Upgrades upgrades;

    @ManyToOne
    @JoinColumn(name = "tg_id")
    @JsonIgnore
    private User user;

    public Character(String name, String faction, Short level, Long upCoins, Long currentXp, Long allXp, Long needXp, Long xpRequirement, Upgrades upgrades, User user) {
        this.name = name;
        this.faction = faction;
        this.level = level;
        this.upCoins = upCoins;
        this.currentXp = currentXp;
        this.allXp = allXp;
        this.needXp = needXp;
        this.xpRequirement = xpRequirement;
        this.upgrades = upgrades;
        this.user = user;
    }
    @Transient
    public Long getUserId(){
        return user.getId();
    }
}