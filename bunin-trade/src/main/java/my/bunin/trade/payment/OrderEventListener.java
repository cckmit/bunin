package my.bunin.trade.payment;

import lombok.extern.slf4j.Slf4j;
import my.bunin.trade.payment.api.PaymentOrderCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {

    @EventHandler
    public void handle(PaymentOrderCreatedEvent event) {
        log.info("payment event handler, payment created event: {}", event);
    }
}
