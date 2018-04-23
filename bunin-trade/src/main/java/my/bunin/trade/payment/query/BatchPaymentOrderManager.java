package my.bunin.trade.payment.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchPaymentOrderManager {

  private BatchPaymentOrderRepository repository;

  public BatchPaymentOrderManager(BatchPaymentOrderRepository repository) {
    this.repository = repository;
  }
}
