package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rsc.clicker_kombat.model.domain.Player;


public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findUserByUsername(String username);

    Player findUserById(Long id);

    @Modifying
    @Query(value = "UPDATE players SET username = :newUsername WHERE id = :id", nativeQuery = true)
    void updateUsername(@Param("id") Long id, @Param("newUsername") String newUsername);
}