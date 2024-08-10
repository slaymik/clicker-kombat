package ru.rsc.clicker_kombat.model.domain;

import lombok.Data;
import lombok.Value;

import java.util.Map;
import java.util.Optional;

@Value
public class Upgrades {
    Map<Integer,Upgrade> upgradeMap;

    public Optional<Upgrade> getUpgrade(Integer id){
        return upgradeMap.get(id) == null? Optional.empty(): Optional.of(upgradeMap.get(id));
    }

   public void add(Upgrade upgrade){
        upgradeMap.put(upgrade.getId(), upgrade);
   }

    @Data
    public static class Upgrade{
        String name;
        String code;
        Long price;
        Short level;
        String description;
        Integer id;
    }
}