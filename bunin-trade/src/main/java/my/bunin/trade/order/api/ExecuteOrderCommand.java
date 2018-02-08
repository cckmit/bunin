package my.bunin.trade.order.api;

import lombok.*;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Getter
@Setter
public class ExecuteOrderCommand {

    @TargetAggregateIdentifier
    private String id;

    private String orderNo;

    private String merchantNo;

}
