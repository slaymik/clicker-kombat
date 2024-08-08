package ru.rsc.clicker_kombat.services.schedulers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.Faction;
import ru.rsc.clicker_kombat.model.domain.FactionProfit;
import ru.rsc.clicker_kombat.repository.FactionProfitRepository;
import ru.rsc.clicker_kombat.repository.FactionRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProfitScheduler {
    private final FactionRepository factionRepository;
    private final FactionProfitRepository factionProfitRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(fixedDelay = 86400000) // будет каждые 10 000 (10 секунд)
    public void scheduleProfitGainForFaction() {
        List<FactionProfit> factionProfits = factionProfitRepository.findAll();

        Map<Long, FactionProfit> factionProfitMap = factionProfits.stream()
                .collect(Collectors.toMap(FactionProfit::getFactionId, fp -> fp));

        List<Faction> activeFactions = factionRepository.findFactionsByIsActive(true);

        if (activeFactions.size() != 2) {
            log.warn("Должно быть 2 активные фракции, а было найдено %d".formatted(activeFactions.size()));
            return;
        }

        Faction faction1 = activeFactions.get(0);
        Faction faction2 = activeFactions.get(1);

        FactionProfit factionProfit1 = factionProfitMap.get(faction1.getId());
        FactionProfit factionProfit2 = factionProfitMap.get(faction2.getId());

        if (factionProfit1 == null || factionProfit2 == null) {
            log.warn("Профит не найден для одной из фракций");
            return;
        }

        double profitSum1 = factionProfit1.getProfitSum();
        double profitSum2 = factionProfit2.getProfitSum();

        double profitDiff = Math.abs(profitSum1 - profitSum2);

        if (profitSum1 > profitSum2) {
            faction1.setProfit((int) ((faction1.getProfit() - profitDiff) * faction1.getProfitMultiplier()));
            faction2.setProfit((int) ((faction2.getProfit() + profitDiff) * faction2.getProfitMultiplier()));
        } else {
            faction2.setProfit((int) ((faction2.getProfit() - profitDiff) * faction2.getProfitMultiplier()));
            faction1.setProfit((int) ((faction1.getProfit() + profitDiff) * faction1.getProfitMultiplier()));
        }

        entityManager.merge(faction1);
        entityManager.merge(faction2);

        log.info("Успех");
    }
}