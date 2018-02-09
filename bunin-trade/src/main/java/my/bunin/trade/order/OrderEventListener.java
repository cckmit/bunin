package my.bunin.trade.order;

import lombok.extern.slf4j.Slf4j;
import my.bunin.trade.order.api.PaymentOrderCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {

    @EventHandler
    public void handle(PaymentOrderCreatedEvent event) {
        log.info("order event handler, order created event: {}", event);
    }
}
