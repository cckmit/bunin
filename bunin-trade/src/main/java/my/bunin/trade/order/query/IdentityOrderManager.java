package my.bunin.trade.order.query;

import org.springframework.stereotype.Component;

@Component
public class IdentityOrderManager {

    private IdentityOrderRepository repository;

    public IdentityOrderManager(IdentityOrderRepository repository) {
        this.repository = repository;
    }

}
