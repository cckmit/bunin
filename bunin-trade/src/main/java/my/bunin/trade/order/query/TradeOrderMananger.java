package my.bunin.trade.order.query;

import org.springframework.stereotype.Component;

@Component
public class TradeOrderMananger {

    private TradeOrderRepository repository;

    public TradeOrderMananger(TradeOrderRepository repository) {
        this.repository = repository;
    }
}
