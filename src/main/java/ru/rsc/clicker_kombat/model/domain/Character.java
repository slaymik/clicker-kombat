package ru.rsc.clicker_kombat.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "character_name")
    private String name;

    @Transient
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "tg_id")
    @JsonIgnore
    private User user;

    public Character(String name, User user) {
        this.name = name;
        this.user = user;
    }
    @Transient
    public Long getUserId(){
        return user.getId();
    }
}