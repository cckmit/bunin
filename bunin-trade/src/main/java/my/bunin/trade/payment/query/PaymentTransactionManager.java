package my.bunin.trade.payment.query;

import org.springframework.stereotype.Component;

@Component
public class PaymentTransactionManager {

  private PaymentTransactionRepository repository;

  public PaymentTransactionManager(PaymentTransactionRepository repository) {
    this.repository = repository;
  }
}
