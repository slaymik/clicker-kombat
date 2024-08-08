package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.Upgrades;
import ru.rsc.clicker_kombat.model.domain.UpgradesEntity;
import ru.rsc.clicker_kombat.model.requests.UpgradesRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.UpgradesRepository;

import java.util.Optional;

import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.*;

@Service
@RequiredArgsConstructor
public class UpgradesService {
    private final UpgradesRepository upgradesRepository;

    public EntityResponse getUpgrades(Long id) {
        Optional<UpgradesEntity> upgrades = upgradesRepository.findById(id);
        if (upgrades.isPresent())
            return getEntityResponseSuccess(upgrades.get());
        else
            return getEntityResponseErrorCharacter(id);
    }

    public void createUpgrades(Long id) {
        UpgradesEntity upgrades = UpgradesEntity.builder()
                .characterId(id)
                .upCoins(0L)
                .upgrades(null)
                .build();
        upgradesRepository.save(upgrades);
    }

    public EntityResponse addUpCoins(UpgradesRequest request) {
        Optional<UpgradesEntity> currentUpgrades = upgradesRepository.findById(request.getCharacterId());
        if (currentUpgrades.isPresent()) {
            UpgradesEntity newUpgrades = UpgradesEntity.builder()
                    .characterId(request.getCharacterId())
                    .upCoins(currentUpgrades.get().getUpCoins() + request.getUpCoins())
                    .upgrades(currentUpgrades.get().getUpgrades())
                    .build();
            upgradesRepository.save(newUpgrades);
            return getEntityResponseSuccess(newUpgrades);
        } else
            return getEntityResponseErrorCharacter(request.getCharacterId());
    }

    public EntityResponse buyUpgrade(UpgradesRequest request) {
        Optional<UpgradesEntity> currentUpgrades = upgradesRepository.findById(request.getCharacterId());
        if (currentUpgrades.isPresent()) {
            Upgrades upgrades = request.getUpgrades();
            Upgrades.Upgrade upgrade = upgrades.getUpgradeList().get((request.getUpgradeId() - 1));
            if (currentUpgrades.get().getUpCoins() >= upgrade.getPrice()) {
                Long upCoins = currentUpgrades.get().getUpCoins() - upgrade.getPrice();
                upgrade.setLevel((short) (upgrade.getLevel() + 1));
                UpgradesEntity newUpgradesEntity = UpgradesEntity.builder()
                        .characterId(request.getCharacterId())
                        .upCoins(upCoins)
                        .upgrades(upgrades)
                        .build();
                upgradesRepository.save(newUpgradesEntity);
                return getEntityResponseSuccess(newUpgradesEntity);
            } else
                return EntityResponse.builder()
                        .status(ERROR)
                        .message("Недостаточно монет")
                        .build();
        } else
            return getEntityResponseErrorCharacter(request.getCharacterId());
    }
}