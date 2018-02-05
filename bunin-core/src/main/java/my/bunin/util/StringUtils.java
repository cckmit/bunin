package my.bunin.util;

import java.util.Map;

public class StringUtils {

    public static final String EMPTY = "";
    public static final String DASH = "-";
    public static final String EQUAL = "=";
    public static final String AMPERSAND = "&";

    public static String pair(Map<String, ?> data) {
        return data.entrySet().stream()
                .map(e -> e.getKey() + EQUAL + e.getValue())
                .reduce((e1, e2) -> e1 + AMPERSAND + e2)
                .orElse(EMPTY);
    }
}
