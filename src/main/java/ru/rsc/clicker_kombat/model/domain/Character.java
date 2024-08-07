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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "character_name")
    private String name;

    @Column(name = "level")
    private Short level;

    @Transient
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "tg_id")
    @JsonIgnore
    private User user;

    public Character(String name, Short level, User user) {
        this.name = name;
        this.level = level;
        this.user = user;
    }
    @Transient
    public Long getUserId(){
        return user.getId();
    }
}