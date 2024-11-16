package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.UserCredentials;

public interface UserRepository extends JpaRepository<UserCredentials, String> {

}