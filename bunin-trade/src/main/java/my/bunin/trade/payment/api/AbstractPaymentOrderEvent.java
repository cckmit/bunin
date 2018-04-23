package my.bunin.trade.payment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import my.bunin.core.AccountType;
import my.bunin.core.CertType;
import my.bunin.core.ChannelType;
import my.bunin.core.CurrencyType;
import my.bunin.core.PaymentType;
import my.bunin.core.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public abstract class AbstractPaymentOrderEvent {
  private final String id;
  private final String orderNo;
  private final String merchantNo;
  private final ChannelType channelType;
  private final TransactionType transactionType;
  private final PaymentType paymentType;
  private final BigDecimal amount;
  private final BigDecimal accountAmount;
  private final String accountNo;
  private final AccountType accountType;
  private final CurrencyType currencyType;
  private final LocalDateTime orderTime;
  private final String identityNo;
  private final String bankAcronym;
  private final String bankAccountNo;
  private final String bankAccountName;
  private final String bankReservedPhone;
  private final String targetBankAcronym;
  private final String targetBankAccountNo;
  private final String targetBankAccountName;
  private final String certNo;
  private final CertType certType;
  private final String callbackUrl;
  private final String returnUrl;
  private final String refundOrderNo;
}
