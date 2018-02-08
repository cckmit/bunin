package my.bunin.trade.order.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "payment_order", uniqueConstraints = @UniqueConstraint(columnNames = {"order_no", "merchant_no"}))
public class PaymentOrder {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Column(name = "order_no", length = 64, nullable = false)
    private String orderNo;

    @Column(name = "merchant_no", length = 64, nullable = false)
    private String merchantNo;

    @Column(name = "channel_type", length = 64)
    private String channelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 64, nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", length = 64, nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "account_amount", nullable = false)
    private BigDecimal accountAmount;

    @Column(name = "account_no", length = 64)
    private String accountNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 64, nullable = false)
    private AccountType accountType;

    @Column(name = "currency_type", length = 64)
    private CurrencyType currencyType;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "execute_time")
    private LocalDateTime executeTime;

    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Column(name = "settlement_date", nullable = false)
    private LocalDate settlementDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false)
    private OrderStatus status;

    @Column
    private String code;

    @Column
    private String message;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private Set<PaymentTransaction> transactions = new HashSet<>();

    private transient String identityNo;

    private transient String bankAcronym;

    private transient String bankAccountNo;

    private transient String bankAccountName;

    private transient String bankReservedPhone;

    private transient String targetBankAcronym;

    private transient String targetBankAccountNo;

    private transient String targetBankAccountName;

    private transient String certNo;

    private transient CertType certType;

    private transient String callbackUrl;

    private transient String redirectUrl;

}
