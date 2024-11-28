package ru.rsc.clicker_kombat.utils.calcs;

import ru.rsc.clicker_kombat.model.domain.LeaderboardRun;
import ru.rsc.clicker_kombat.model.domain.Run;

import java.util.List;

public class RatingCalc {

    public static int calculateRating(List<LeaderboardRun> runs, int limit) {
        int rating = 0;
        for (LeaderboardRun leaderboardRun : runs) {
            Run run = leaderboardRun.getRun();
            if (run.getIsHeroic()) {
                rating += run.getLevel();
            }
            rating += Math.min(run.getLevel(), limit);
        }
        return rating;
    }
}
