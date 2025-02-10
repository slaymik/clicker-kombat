package ru.rsc.clicker_kombat.utils.calcs;


public class XpCalc {
    public static Integer getAllXp(Integer currentXp, Integer allXp, Short currentLevel, Short neededLevel) {
        for (short i = (short) (currentLevel + 1); i <= neededLevel; i++) {
            int needXp = calcXpRequired(i);
            allXp += needXp;
        }
        return allXp + currentXp;
    }

    public static Integer calcXpRequired(Short currentLevel) {
        if (currentLevel >= 10) {
            return (currentLevel + 1) * (currentLevel + 1) * 10;
        }
        return (currentLevel +1) * 100;
    }
}