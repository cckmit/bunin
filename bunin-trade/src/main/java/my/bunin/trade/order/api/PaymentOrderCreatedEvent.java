package my.bunin.trade.order.api;

import lombok.Getter;
import my.bunin.core.*;

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
