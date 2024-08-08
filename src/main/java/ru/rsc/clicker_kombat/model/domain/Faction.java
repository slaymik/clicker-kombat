package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "factions")
public class Faction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Setter
    @Column(name = "profit")
    private Integer profit;

    @Column(name = "profit_balancing_multiplier")
    private Double profitMultiplier;

    @Setter
    @Column(name = "is_active")
    private Boolean isActive;
}