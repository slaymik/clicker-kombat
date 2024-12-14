package ru.rsc.clicker_kombat.utils.calcs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpCoinsCalc {
    private static final int BASE_VALUE = 10;
    private static final int ADD_VALUE = 2;
    private static final int BASE_HEROIC_VALUE = 20;
    private static final float HEROIC_SCALE = 1.25f;
    private static final float VICTORY_SCALE = 2.0f;
    private static final int LEVELS_IN_WORLD = 6;

    public static int calculate(int level, boolean isHeroic, boolean isVictory) {
        int value = isHeroic ? calculateHeroic(level) : calculateSimple(level);
        return isVictory ? Math.round(value * VICTORY_SCALE) : value;
    }

    private static int calculateSimple(int level) {
        int result = 0;

        for (int l = 0; l < level; l++) {
            int world = l / LEVELS_IN_WORLD;
            int add = BASE_VALUE + ADD_VALUE * world;
            result += add;
        }

        return result;
    }

    private static int calculateHeroic(int level) {
        float result = 0;

        for (int l = 0; l < level; l++) {
            int world = l / LEVELS_IN_WORLD;
            float add = (float) (BASE_HEROIC_VALUE * Math.pow(HEROIC_SCALE, world));
            result += add;
        }

        return Math.round(result);
    }
}
