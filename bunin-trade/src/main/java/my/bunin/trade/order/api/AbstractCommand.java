package my.bunin.trade.order.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
@Setter
@ToString
public class AbstractCommand {

    @TargetAggregateIdentifier
    private String id;
}
