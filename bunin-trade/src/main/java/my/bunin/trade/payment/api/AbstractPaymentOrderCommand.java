package my.bunin.trade.payment.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.AccountType;
import my.bunin.core.CertType;
import my.bunin.core.ChannelType;
import my.bunin.core.CurrencyType;
import my.bunin.core.PaymentType;
import my.bunin.core.TransactionType;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public abstract class AbstractPaymentOrderCommand {

  @TargetAggregateIdentifier
  private String id;

  @NotEmpty
  private String orderNo;

  @NotEmpty
  private String merchantNo;

  private ChannelType channelType;

  @NotNull
  private TransactionType transactionType;

  @NotNull
  private PaymentType paymentType;

  @NotNull
  private BigDecimal amount;

  private BigDecimal accountAmount;

  private String accountNo;

  @NotNull
  private AccountType accountType;

  private CurrencyType currencyType = CurrencyType.CNY;

  @NotNull
  private LocalDateTime orderTime;

  private String identityNo;

  private String bankAcronym;

  private String bankAccountNo;

  private String bankAccountName;

  private String bankReservedPhone;

  private String targetBankAcronym;

  private String targetBankAccountNo;

  private String targetBankAccountName;

  private String certNo;

  private CertType certType;

  private String callbackUrl;

  private String returnUrl;

  private String refundOrderNo;
}
