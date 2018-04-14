package my.bunin.reconciliation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
public class ListResult {

    private String orderNo;
    private String originStatus;
    private String targetStatus;
    private String result;
}
