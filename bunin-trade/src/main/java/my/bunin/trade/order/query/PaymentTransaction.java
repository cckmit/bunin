package my.bunin.trade.order.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "payment_transaction", uniqueConstraints = @UniqueConstraint(columnNames = {"serial_no", "merchant_no", "channel_no"}))
public class PaymentTransaction {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private PaymentOrder order;

    @Column(name = "serial_no", length = 64, nullable = false)
    private String serialNo;

    @Column(name = "merchant_no", length = 64, nullable = false)
    private String merchantNo;

    @Column(name = "channel_no", length = 64, nullable = false)
    private String channelNo;

    @Column(name = "channel_type", length = 64, nullable = false)
    private String channelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 64, nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", length = 64, nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "account_no", length = 64)
    private String accountNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 64, nullable = false)
    private AccountType accountType;

    @Column(name = "currency_type", length = 64)
    private CurrencyType currencyType;

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
    private TransactionStatus status;

    @Column
    private String code;

    @Column
    private String message;

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
