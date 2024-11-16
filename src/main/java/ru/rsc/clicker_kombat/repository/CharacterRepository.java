package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.Character;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character,Long> {
    Character getCharacterByNameAndPlayer_Id(String name, Long UserId);

    List<Character> getCharactersByPlayer_Id(Long id);
}