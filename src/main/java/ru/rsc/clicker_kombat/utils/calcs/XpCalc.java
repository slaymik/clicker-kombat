package ru.rsc.clicker_kombat.utils.calcs;

import ru.rsc.clicker_kombat.consts.XPConsts;

public class XpCalc {
    public static Long getAllXp(Long currentXp, Long allXp, Short currentLevel, Short neededLevel){
        for(short i = (short) (currentLevel + 1); i <= neededLevel; i++){
            short xpMulti = XPConsts.getXpMultiplier(i);
            long needXp = (long) XPConsts.BASE_NEED_XP * xpMulti;
            allXp+=needXp;
        }
        return allXp+currentXp;
    }
}