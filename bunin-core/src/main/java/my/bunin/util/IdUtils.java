package my.bunin.util;

import java.util.UUID;

public class IdUtils {

  public static String uuidWithNoDash() {
    return UUID.randomUUID().toString().replaceAll(StringUtils.DASH, StringUtils.EMPTY);
  }

  public static String uuid() {
    return UUID.randomUUID().toString();
  }
}
