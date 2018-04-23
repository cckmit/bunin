package my.bunin.trade.payment;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import lombok.extern.slf4j.Slf4j;
import my.bunin.core.AccountType;
import my.bunin.core.CertType;
import my.bunin.core.ChannelType;
import my.bunin.core.CurrencyType;
import my.bunin.core.OrderStatus;
import my.bunin.core.PaymentType;
import my.bunin.core.TransactionType;
import my.bunin.trade.payment.api.CreatePaymentOrderCommand;
import my.bunin.trade.payment.api.ExecutePaymentOrderCommand;
import my.bunin.trade.payment.api.PaymentOrderCreatedEvent;
import my.bunin.trade.payment.api.PaymentOrderExecutedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;



@Slf4j
@Aggregate
public class OrderAggregate {

  @AggregateIdentifier
  private String id;
  private String orderNo;
  private String merchantNo;
  private ChannelType channelType;
  private TransactionType transactionType;
  private PaymentType paymentType;
  private BigDecimal amount;
  private BigDecimal refundAmount;
  private BigDecimal accountAmount;
  private BigDecimal accountRefundAmount;
  private String accountNo;
  private AccountType accountType;
  private CurrencyType currencyType;
  private LocalDateTime orderTime;
  private LocalDateTime executeTime;
  private LocalDateTime completeTime;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private LocalDate settlementDate;
  private OrderStatus status;
  private String code;
  private String message;
  private boolean hasRefund;
  private String refundOrderNo;
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

  protected OrderAggregate() {
  }

  /**
   * crete payment order.
   */
  @CommandHandler
  public OrderAggregate(CreatePaymentOrderCommand command) {
    log.info("payment aggregate create payment command: {}", command);

    apply(PaymentOrderCreatedEvent.builder()
        .orderNo(command.getOrderNo())
        .merchantNo(command.getMerchantNo())
        .amount(command.getAmount())
        .build());
  }

  public void execute(ExecutePaymentOrderCommand command) {
    log.info("payment aggregate execute payment command: {}", command);
    apply(new PaymentOrderExecutedEvent(id, orderNo, merchantNo, amount));
  }

  /**
   * when payment order created.
   */
  @EventSourcingHandler
  public void on(PaymentOrderCreatedEvent event) {
    log.info("payment aggregate payment created event: {}", event);
    this.id = event.getId();
    this.orderNo = event.getOrderNo();
    this.merchantNo = event.getMerchantNo();
    this.amount = event.getAmount();
    this.status = OrderStatus.CREATED;
  }

  @EventSourcingHandler
  public void on(PaymentOrderExecutedEvent event) {
    log.info("payment aggregate payment executed event: {}", event);
    this.status = OrderStatus.PROCEESING;
  }


}
