package ru.rsc.clicker_kombat.model.domain;

import lombok.Value;

import java.util.List;

@Value
public class Upgrades {
    List<Upgrade> upgradeList;
    @Value
    public static class Upgrade{
        String name;
        Short level;
        String description;
        Integer id;
    }
}