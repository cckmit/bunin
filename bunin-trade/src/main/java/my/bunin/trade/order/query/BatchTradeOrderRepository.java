package my.bunin.trade.order.query;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchTradeOrderRepository extends JpaRepository<BatchTradeOrder, Long> {
}
