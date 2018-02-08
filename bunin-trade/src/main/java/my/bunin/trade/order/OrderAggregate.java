package my.bunin.trade.order;

import lombok.extern.slf4j.Slf4j;
import my.bunin.trade.order.api.CreateOrderCommand;
import my.bunin.trade.order.api.ExecuteOrderCommand;
import my.bunin.trade.order.api.OrderCreatedEvent;
import my.bunin.trade.order.api.OrderExecutedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Slf4j
@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String id;
    private String orderNo;
    private String merchantNo;
    private BigDecimal amount;
    private String status;

    protected OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        log.info("order aggregate create order command: {}", command);

        apply(new OrderCreatedEvent(command.getId(), command.getOrderNo(),
                command.getMerchantNo(), command.getAmount()));
    }

    public void execute(ExecuteOrderCommand command) {
        log.info("order aggregate execute order command: {}", command);
        apply(new OrderExecutedEvent(command.getId(), command.getOrderNo(),
                command.getMerchantNo(), command.getAmount()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        log.info("order aggregate order created event: {}", event);
        this.id = event.getId();
        this.orderNo = event.getOrderNo();
        this.merchantNo = event.getMerchantNo();
        this.amount = event.getAmount();
        this.status = "CREATED";
    }

}
