package ru.rsc.clicker_kombat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsc.clicker_kombat.model.domain.CharacterInfo;

public interface CharacterInfoRepository extends JpaRepository<CharacterInfo,Long> {
}