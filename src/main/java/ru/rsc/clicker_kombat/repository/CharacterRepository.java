package ru.rsc.clicker_kombat.repository;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rsc.clicker_kombat.model.domain.Character;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, Integer> {

    List<Character> getCharactersByPlayer_Id(UUID id);
    Optional<Character> getCharacterByPlayer_IdAndCharacterGameId(UUID playerId, Integer characterGameId);

    @Query("update Character c set c.items = :items where c.id = :id")
    @Modifying
    void updateItems(@Param("id") int characterId, @Param("items") ArrayNode items);
}