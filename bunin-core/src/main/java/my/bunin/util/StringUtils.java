package my.bunin.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Slf4j
public class StringUtils {



    public static final String EMPTY = "";
    public static final String DASH = "-";
    public static final String EQUAL = "=";
    public static final String AMPERSAND = "&";
    public static final String VERTICAL = "|";

    public static final String UTF8 = "utf-8";

    public static String pair(Map<String, String> data) {
        return data.entrySet().stream()
                .map(e -> e.getKey() + EQUAL + e.getValue())
                .reduce((e1, e2) -> e1 + AMPERSAND + e2)
                .orElse(EMPTY);
    }

    public static String encodePair(Map<String, String> data) {
        return data.entrySet().stream()
                .map(e -> e.getKey() + EQUAL + encode( e.getValue()))
                .reduce((e1, e2) -> e1 + AMPERSAND + e2)
                .orElse(EMPTY);
    }

    public static String encode(String source) {
        try {
            return URLEncoder.encode(source, UTF8);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            return source;
        }

    }


}
