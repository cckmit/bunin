package my.bunin.trade.payment.api;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PaymentOrderExecutedEvent {

    private String id;
    private String orderNo;
    private String merchantNo;
    private BigDecimal amount;
}
