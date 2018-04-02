package my.bunin.util;

import java.math.BigDecimal;

public class NumberUtils {

    public static BigDecimal removeDecimalPoint(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_DOWN);
    }
}
