package my.bunin.reconciliation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class BatchReconciliationTest {

    private JdbcTemplate jdbcTemplate;

    public void run() {
//        jdbcTemplate.batchUpdate("", new ArrayList<>());
    }

    public static void main(String[] args) {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String rand = StringUtils.leftPad(String.valueOf(new Random(100).nextInt()), 4, "0");
        System.out.println("10" + "C11" + "1003" +"0001" + ts + rand);
    }
}
