package my.bunin.trade.payment.query;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchPaymentOrderRepository extends JpaRepository<BatchPaymentOrder, Long> {
}
