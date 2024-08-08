package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.CharacterInfo;

import java.util.Optional;

public interface CharacterInfoRepository extends JpaRepository<CharacterInfo,Long> {
    Optional<CharacterInfo> findCharacterInfoByCharacter_Id(Long id);
}