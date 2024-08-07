package ru.rsc.clicker_kombat.consts;

public class XPConsts {
    public static final Short BASE_XP = 0;
    public static final Integer BASE_NEED_XP = 100;
    public static final Short NEED_XP_MULTIPLIER_FOR_1_THROUGH_10 = 1;
    public static final Short NEED_XP_MULTIPLIER_FOR_11_THROUGH_20 = 2;
    public static final Short NEED_XP_MULTIPLIER_FOR_21_THROUGH_30 = 3;
    public static final Short NEED_XP_MULTIPLIER_FOR_31_THROUGH_40 = 4;
    public static final Short NEED_XP_MULTIPLIER_FOR_41_THROUGH_50 = 5;
    public static final Short NEED_XP_MULTIPLIER_FOR_51_THROUGH_60 = 6;
    public static final Short NEED_XP_MULTIPLIER_FOR_61_THROUGH_70 = 7;
    public static final Short NEED_XP_MULTIPLIER_FOR_71_THROUGH_80 = 8;
    public static final Short NEED_XP_MULTIPLIER_FOR_81_THROUGH_90 = 9;
    public static final Short NEED_XP_MULTIPLIER_FOR_91_THROUGH_100 = 10;

        public static short getXpMultiplier(Short level) {
            return switch (level) {
                case Short l when l >= 11 && l <= 20 -> NEED_XP_MULTIPLIER_FOR_11_THROUGH_20;
                case Short l when l >= 21 && l <= 30 -> NEED_XP_MULTIPLIER_FOR_21_THROUGH_30;
                case Short l when l >= 31 && l <= 40 -> NEED_XP_MULTIPLIER_FOR_31_THROUGH_40;
                case Short l when l >= 41 && l <= 50 -> NEED_XP_MULTIPLIER_FOR_41_THROUGH_50;
                case Short l when l >= 51 && l <= 60 -> NEED_XP_MULTIPLIER_FOR_51_THROUGH_60;
                case Short l when l >= 61 && l <= 70 -> NEED_XP_MULTIPLIER_FOR_61_THROUGH_70;
                case Short l when l >= 71 && l <= 80 -> NEED_XP_MULTIPLIER_FOR_71_THROUGH_80;
                case Short l when l >= 81 && l <= 90 -> NEED_XP_MULTIPLIER_FOR_81_THROUGH_90;
                case Short l when l >= 91 && l <= 100 -> NEED_XP_MULTIPLIER_FOR_91_THROUGH_100;
                default -> NEED_XP_MULTIPLIER_FOR_1_THROUGH_10;
            };
        }
    }