package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.rsc.clicker_kombat.utils.converters.UpgradesConverter;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "character_upgrades")
public class UpgradesEntity {
    @Id
    @Column(name = "character_id")
    private Long characterId;

    @Column(name = "up_coins")
    private Long upCoins;

    @Column(name = "upgrades")
    @Convert(converter = UpgradesConverter.class)
    private Upgrades upgrades;
}