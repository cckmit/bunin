package my.bunin.trade.order.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.BatchOrderStatus;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "batch_trade_order", uniqueConstraints = @UniqueConstraint(columnNames = {"batch_no", "merchant_no"}))
public class BatchTradeOrder {

    @Id
    private Long id;

    @Version
    private Long version;

    @Column(name = "batch_no", length = 64, nullable = false)
    private String batchNo;

    @Column(name = "merchant_no", length = 64, nullable = false)
    private String merchantNo;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false)
    private BatchOrderStatus status;
}
