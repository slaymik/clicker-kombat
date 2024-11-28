package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.PlayerRating;

import java.util.UUID;

public interface PlayerRatingRepository extends JpaRepository<PlayerRating, UUID> {
}