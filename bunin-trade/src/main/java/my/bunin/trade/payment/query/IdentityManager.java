package my.bunin.trade.payment.query;

import org.springframework.stereotype.Component;

@Component
public class IdentityManager {

    private IdentityRepository repository;

    public IdentityManager(IdentityRepository repository) {
        this.repository = repository;
    }
}
