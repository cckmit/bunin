package my.bunin.trade.order.api;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
public class CreateOrderCommand extends AbstractCommand{

    private String orderNo;
    private String merchantNo;
    private BigDecimal amount;
}
