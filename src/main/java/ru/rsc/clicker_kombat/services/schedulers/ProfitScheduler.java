package ru.rsc.clicker_kombat.services.schedulers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.Faction;
import ru.rsc.clicker_kombat.model.domain.FactionProfit;
import ru.rsc.clicker_kombat.repository.FactionProfitRepository;
import ru.rsc.clicker_kombat.repository.FactionRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.rsc.clicker_kombat.consts.FactionConsts.HALF_OF_OVERALL_PROFIT;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProfitScheduler {
//    @Value("${gameplay.coef.faction.profit.multiplier}")
//    private Double factionCoef;
//
//    private final FactionRepository factionRepository;
//    private final FactionProfitRepository factionProfitRepository;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Scheduled(fixedDelay = 86400000) // будет каждые 60 000 (1 минута)
//    public void scheduleProfitGainForFaction() {
//        List<FactionProfit> factionProfits = factionProfitRepository.findAll();
//
//        Map<Long, FactionProfit> factionProfitMap = factionProfits.stream()
//                .collect(Collectors.toMap(FactionProfit::getFactionId, fp -> fp));
//
//        List<Faction> activeFactions = factionRepository.findFactionsByIsActive(true);
//
//        if (activeFactions.size() != 2) {
//            log.warn("Должно быть 2 активные фракции, а было найдено %d".formatted(activeFactions.size()));
//            return;
//        }
//
//        Faction faction1 = activeFactions.get(0);
//        Faction faction2 = activeFactions.get(1);
//
//        FactionProfit factionProfit1 = factionProfitMap.get(faction1.getId());
//        FactionProfit factionProfit2 = factionProfitMap.get(faction2.getId());
//
//        if (factionProfit1 == null || factionProfit2 == null) {
//            log.warn("Профит не найден для одной из фракций");
//            return;
//        }
//        updateFactionProfits(factionProfit1, factionProfit2, faction1, faction2);
//
//        log.info("Успех");
//    }
//
//    private void updateFactionProfits(FactionProfit factionProfit1, FactionProfit factionProfit2, Faction faction1, Faction faction2) {
//        double profitMultiplier;
//        int faction1Count = factionProfit1.getCount();
//        int faction2Count = factionProfit2.getCount();
//
//        double profitSum1 = factionProfit1.getProfitSum();
//        double profitSum2 = factionProfit2.getProfitSum();
//
//        if (faction1Count > faction2Count) {
//            profitMultiplier = ((double) faction1Count / faction2Count - 1) * factionCoef + 1;
//            profitSum2 *= profitMultiplier;
//        } else {
//            profitMultiplier = ((double) faction2Count / faction1Count - 1) * factionCoef + 1;
//            profitSum1 *= profitMultiplier;
//        }
//        double profitDiff = Math.abs(profitSum1 - profitSum2);
//
//        if (profitSum1 < profitSum2) {
//            faction1.setProfit((int) (faction1.getProfit() - profitDiff));
//            faction2.setProfit((int) (faction2.getProfit() + profitDiff));
//        } else {
//            faction2.setProfit((int) (faction2.getProfit() - profitDiff));
//            faction1.setProfit((int) (faction1.getProfit() + profitDiff));
//        }
//        if (faction1.getProfit() < 0) {
//            faction1.setProfit(0);
//            faction2.setProfit(HALF_OF_OVERALL_PROFIT * 2);
//        } else if (faction2.getProfit() < 0) {
//            faction2.setProfit(0);
//            faction1.setProfit(HALF_OF_OVERALL_PROFIT * 2);
//        }
//        entityManager.merge(faction1);
//        entityManager.merge(faction2);
//    }
}