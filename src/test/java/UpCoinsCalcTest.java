import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.rsc.clicker_kombat.utils.calcs.UpCoinsCalc;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpCoinsCalcTest {

    @ParameterizedTest
    @CsvSource({
            "10, 108",
            "30, 420",
            "45, 744",
            "60, 1140",
            "90, 2160"
    })
    void testUpCoinsCalc(int level, int expectedCoins) {
        int actual = UpCoinsCalc.calculate(level, false, false);
        assertEquals(expectedCoins, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 216",
            "30, 840",
            "45, 1488",
            "60, 2280",
            "90, 4320"
    })
    void testUpCoinsCalcWhenWin(int level, int expectedCoins) {
        int actual = UpCoinsCalc.calculate(level, false, true);
        assertEquals(expectedCoins, actual);
    }
}
