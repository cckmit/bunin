package my.bunin.trade.payment.api;

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
public class PaymentOrderCreatedEvent extends AbstractPaymentOrderEvent {

  @lombok.Builder(builderClassName = "Builder")
  private PaymentOrderCreatedEvent(String id,
                                   String orderNo,
                                   String merchantNo,
                                   ChannelType channelType,
                                   TransactionType transactionType,
                                   PaymentType paymentType,
                                   BigDecimal amount,
                                   BigDecimal accountAmount,
                                   String accountNo,
                                   AccountType accountType,
                                   CurrencyType currencyType,
                                   LocalDateTime orderTime,
                                   String identityNo,
                                   String bankAcronym,
                                   String bankAccountNo,
                                   String bankAccountName,
                                   String bankReservedPhone,
                                   String targetBankAcronym,
                                   String targetBankAccountNo,
                                   String targetBankAccountName,
                                   String certNo,
                                   CertType certType,
                                   String callbackUrl,
                                   String returnUrl,
                                   String refundOrderNo) {
    super(id, orderNo, merchantNo, channelType, transactionType,
        paymentType, amount, accountAmount, accountNo,
        accountType, currencyType, orderTime, identityNo,
        bankAcronym, bankAccountNo, bankAccountName, bankReservedPhone,
        targetBankAcronym, targetBankAccountNo, targetBankAccountName,
        certNo, certType, callbackUrl, returnUrl, refundOrderNo);
  }


}
