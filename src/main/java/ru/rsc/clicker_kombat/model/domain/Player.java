package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Setter
    @Column(name = "username")
    private String username;

    @Column(name = "login")
    private String login;

    @Column(name = "registration_date")
    private Instant registrationDate;

    @Setter
    @Column(name = "last_online")
    private Instant lastOnline;

    @Column(name = "is_active")
    private Boolean isActive;

    @Setter
    @Column(name = "up_coins")
    private Long upCoins;

    @Setter
    @Column(name = "session")
    private Integer session;

    @Setter
    @Column(name = "rating")
    private Integer rating;

    @OneToMany(mappedBy = "player")
    private List<Character> characters;
}