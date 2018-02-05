package my.bunin.util;

import java.util.UUID;

import static my.bunin.util.StringUtils.DASH;
import static my.bunin.util.StringUtils.EMPTY;

public class IdUtils {

    public static String uuidWithNoDash() {
        return UUID.randomUUID().toString().replaceAll(DASH, EMPTY);
    }

    public static String uuid(){
        return UUID.randomUUID().toString();
    }
}
