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
public class User {
    @Id
    @Column(name = "tg_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "token")
    private String token;

    @Column(name = "registration_date")
    private Instant registrationDate;

    @Column(name = "last_online")
    private Instant lastOnline;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "user")
    private List<Character> characters;

    public User(String username, String token, Instant registrationDate, Instant lastOnline, Boolean isActive, List<Character> characters) {
        this.username = username;
        this.token = token;
        this.registrationDate = registrationDate;
        this.lastOnline = lastOnline;
        this.isActive = isActive;
        this.characters = characters;
    }
}