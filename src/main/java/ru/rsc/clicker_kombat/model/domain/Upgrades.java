package ru.rsc.clicker_kombat.model.domain;

import lombok.Data;
import lombok.Value;

import java.util.List;

@Value
public class Upgrades {
    List<Upgrade> upgradeList;
    @Data
    public static class Upgrade{
        String name;
        Long price;
        Short level;
        String description;
        Long id;
    }
}