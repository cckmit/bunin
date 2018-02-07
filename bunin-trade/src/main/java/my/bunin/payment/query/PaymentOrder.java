package my.bunin.payment.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.AccountType;
import my.bunin.core.PaymentType;
import my.bunin.core.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PaymentOrder {

    private String orderNo;
    private String merchantNo;
    private String channelType;
    private TransactionType transactionType;
    private PaymentType paymentType;
    private BigDecimal amount;
    private BigDecimal accountAmount;
    private String accountNo;
    private AccountType accountType;
    private String identityNo;
    private String bankAcronym;
    private String bankAccountNo;
    private String bankAccountName;
    private String bankReservedPhone;
    private String certNo;
    private String certType;
    private String currencyType;
    private LocalDateTime orderTime;
    private LocalDateTime executeTime;
    private LocalDateTime completeTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDate settlementDate;
    private String status;
    private String callbackUrl;
    private String redirectUrl;
    private String code;
    private String message;

}
