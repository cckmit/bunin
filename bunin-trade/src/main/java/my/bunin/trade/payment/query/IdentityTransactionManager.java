package my.bunin.trade.payment.query;

import org.springframework.stereotype.Component;

@Component
public class IdentityTransactionManager {

  private IdentityTransactionRepository repository;

  public IdentityTransactionManager(IdentityTransactionRepository repository) {
    this.repository = repository;
  }
}
