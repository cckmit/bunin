package my.bunin.trade.order.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "batch_payment_order", uniqueConstraints = @UniqueConstraint(columnNames = {"batch_no", "merchant_no"}))
public class BatchPaymentOrder {

    @Id
    private String id;

    @Version
    private Long version;

    @Column(name = "batch_no", length = 64, nullable = false)
    private String batchNo;
}
