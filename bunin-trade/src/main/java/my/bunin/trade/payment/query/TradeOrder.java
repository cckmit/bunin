package my.bunin.trade.payment.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.AccountType;
import my.bunin.core.CertType;
import my.bunin.core.ChannelType;
import my.bunin.core.CurrencyType;
import my.bunin.core.OrderStatus;
import my.bunin.core.PaymentType;
import my.bunin.core.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Getter
@Setter
@ToString
@Entity
@Table(name = "trade_order",
    uniqueConstraints = @UniqueConstraint(columnNames = {"trade_no", "merchant_no"}))
public class TradeOrder {

  @Id
  private String id;

  @Version
  private Long version;

  @Column(name = "trade_no", length = 64, nullable = false)
  private String tradeNo;

  @Column(name = "merchant_no", length = 64, nullable = false)
  private String merchantNo;

  @Enumerated(EnumType.STRING)
  @Column(name = "channel_type", length = 64)
  private ChannelType channelType;

  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_type", length = 64, nullable = false)
  private TransactionType transactionType;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_type", length = 64, nullable = false)
  private PaymentType paymentType;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(name = "refund_amount", nullable = false)
  private BigDecimal refundAmount;

  @Column(name = "account_amount", nullable = false)
  private BigDecimal accountAmount;

  @Column(name = "account_refund_amount", nullable = false)
  private BigDecimal accountRefundAmount;

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

  @Column(name = "has_refund", columnDefinition = "TINYINT", nullable = false)
  private boolean hasRefund;

  @Column(name = "refund_order_no", length = 64)
  private String refundOrderNo;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "payment")
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

  private transient String returnUrl;
}
