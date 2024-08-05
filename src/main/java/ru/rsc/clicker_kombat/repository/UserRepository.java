package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rsc.clicker_kombat.model.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User findUserById(Long id);

    @Modifying
    @Query(value = "UPDATE players SET username = :newUsername WHERE tg_id = :id", nativeQuery = true)
    void updateUsername(@Param("id") Long id, @Param("newUsername") String newUsername);
}