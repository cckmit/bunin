package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;
import my.bunin.core.AccountType;
import my.bunin.core.BankAcronym;
import my.bunin.core.CertType;
import my.bunin.core.ChannelType;
import my.bunin.core.CurrencyType;
import my.bunin.core.PaymentType;
import my.bunin.core.RequestStage;
import my.bunin.core.TransactionStatus;
import my.bunin.core.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction {

  private String orderNo;

  private String serialNo;

  private String merchantNo;

  private String channelNo;

  private ChannelType channelType;

  private TransactionType transactionType;

  private PaymentType paymentType;

  private RequestStage stage = RequestStage.WHOLE;

  private BigDecimal amount;

  private String accountNo;

  private AccountType accountType;

  private CurrencyType currencyType;

  private LocalDateTime executeTime;

  private LocalDateTime completeTime;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

  private LocalDate settlementDate;

  private TransactionStatus status;

  private String code;

  private String message;

  private String identityNo;

  private BankAcronym bankAcronym;

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

}
