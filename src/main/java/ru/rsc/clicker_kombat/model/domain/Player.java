package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "players")
public class Player {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "login")
    private String login;

    @Column(name = "token")
    private String token;

    @Column(name = "registration_date")
    private Instant registrationDate;

    @Column(name = "last_online")
    private Instant lastOnline;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "player")
    private List<Character> characters;

    public Player(String username, String token, Instant registrationDate, Instant lastOnline, Boolean isActive, List<Character> characters, String login) {
        this.username = username;
        this.token = token;
        this.login = login;
        this.registrationDate = registrationDate;
        this.lastOnline = lastOnline;
        this.isActive = isActive;
        this.characters = characters;
    }
}