package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.Faction;
import ru.rsc.clicker_kombat.model.requests.FactionRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.FactionRepository;

import java.util.Optional;

import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.*;
import static ru.rsc.clicker_kombat.consts.FactionConsts.HALF_OF_OVERALL_PROFIT;

@Service
@RequiredArgsConstructor
public class FactionService {
    private final FactionRepository factionRepository;

    public EntityResponse createFaction(FactionRequest request) {
        Faction faction = Faction.builder()
                .name(request.getName())
                .description(request.getDescription())
                .isActive(true)
                .profit(HALF_OF_OVERALL_PROFIT)
                .build();
        factionRepository.save(faction);
        return getEntityResponseSuccess(faction);
    }

    public EntityResponse getFactionResponse(Long id) {
        Optional<Faction> faction = factionRepository.findById(id);
        if (faction.isPresent())
            return getEntityResponseSuccess(faction);
        else
            return getEntityResponseErrorFaction(id);
    }

    public Optional<Faction> getFaction(Long id) {
        return factionRepository.findById(id);
    }

    public EntityResponse setFactionToInactive(Long id) {
        Optional<Faction> faction = factionRepository.findById(id);
        if (faction.isPresent()) {
            faction.get().setIsActive(false);
            factionRepository.save(faction.get());
            return getEntityResponseSuccess(faction);
        } else
            return getEntityResponseErrorFaction(id);
    }
}