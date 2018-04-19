package my.bunin.trade.payment.query;

import org.springframework.stereotype.Component;

@Component
public class IdentityOrderManager {

    private IdentityOrderRepository repository;

    public IdentityOrderManager(IdentityOrderRepository repository) {
        this.repository = repository;
    }

}
