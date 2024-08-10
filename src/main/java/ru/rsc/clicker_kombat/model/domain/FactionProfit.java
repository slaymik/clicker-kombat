package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@Table(name = "faction_profits")
public class FactionProfit {
    @Id
    @Column(name = "faction_id")
    private Long factionId;

    @Column(name = "faction_name")
    private String factionName;

    @Column(name = "profit_sum")
    private Long profitSum;

    @Column(name = "character_count")
    private Integer count;

    @Column(name = "is_active")
    private Boolean isActive;
}
