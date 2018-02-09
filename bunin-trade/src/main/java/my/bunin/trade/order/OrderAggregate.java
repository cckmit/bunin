package my.bunin.trade.order;

import lombok.extern.slf4j.Slf4j;
import my.bunin.core.*;
import my.bunin.trade.channel.query.Channel;
import my.bunin.trade.order.api.CreatePaymentOrderCommand;
import my.bunin.trade.order.api.ExecutePaymentOrderCommand;
import my.bunin.trade.order.api.PaymentOrderCreatedEvent;
import my.bunin.trade.order.api.PaymentOrderExecutedEvent;
import my.bunin.trade.order.query.PaymentTransaction;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

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

    @CommandHandler
    public OrderAggregate(CreatePaymentOrderCommand command) {
        log.info("order aggregate create order command: {}", command);

        apply(PaymentOrderCreatedEvent.builder()
                .orderNo(command.getOrderNo())
                .merchantNo(command.getMerchantNo())
                .amount(command.getAmount())
                .build());
    }

    public void execute(ExecutePaymentOrderCommand command) {
        log.info("order aggregate execute order command: {}", command);
        apply(new PaymentOrderExecutedEvent(id, orderNo, merchantNo, amount));
    }

    @EventSourcingHandler
    public void on(PaymentOrderCreatedEvent event) {
        log.info("order aggregate order created event: {}", event);
        this.id = event.getId();
        this.orderNo = event.getOrderNo();
        this.merchantNo = event.getMerchantNo();
        this.amount = event.getAmount();
        this.status = OrderStatus.CREATED;
    }

    @EventSourcingHandler
    public void on(PaymentOrderExecutedEvent event) {
        log.info("order aggregate order executed event: {}", event);
        this.status = OrderStatus.PROCEESING;
    }


}
