package my.bunin.trade.order.api;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderExecutedEvent{

    private String id;
    private String orderNo;
    private String merchantNo;
    private BigDecimal amount;
}
