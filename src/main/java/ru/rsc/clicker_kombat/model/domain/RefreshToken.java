package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "refresh_token")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @Column(nullable = false, unique = true, name = "token")
    private UUID token;

    @Column(name = "username")
    private String username;

    @Column(nullable = false, name = "expiry_date")
    @Setter
    private Instant expiryDate;
}