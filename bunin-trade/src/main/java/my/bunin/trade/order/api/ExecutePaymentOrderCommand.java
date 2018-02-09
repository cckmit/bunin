package my.bunin.trade.order.api;

import lombok.*;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
public class ExecutePaymentOrderCommand extends AbstractPaymentOrderCommand {

}
