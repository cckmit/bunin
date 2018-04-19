package my.bunin.trade.payment.query;

import org.springframework.stereotype.Component;

@Component
public class PaymentOrderManager {

    private PaymentOrderRepository repository;

    public PaymentOrderManager(PaymentOrderRepository repository) {
        this.repository = repository;
    }
}
