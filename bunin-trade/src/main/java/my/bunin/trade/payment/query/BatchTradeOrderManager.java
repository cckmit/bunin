package my.bunin.trade.payment.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchTradeOrderManager {

    private BatchTradeOrderRepository repository;

    public BatchTradeOrderManager(BatchTradeOrderRepository repository) {
        this.repository = repository;
    }
}
