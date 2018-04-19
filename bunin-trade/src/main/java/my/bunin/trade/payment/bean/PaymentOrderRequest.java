package my.bunin.trade.payment.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.AccountType;
import my.bunin.core.CertType;
import my.bunin.core.CurrencyType;
import my.bunin.core.TransactionType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static my.bunin.util.JacksonUtils.ISO_DATE_TIME_PATTERN;

@Getter
@Setter
@ToString
public class PaymentOrderRequest {

    @NotEmpty
    @Length(max = 64)
    private String orderNo;

    @NotEmpty
    private String merchantNo;

    private String channelType;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private BigDecimal amount;

    private String accountNo;

    @NotNull
    private AccountType accountType;

    private String identityNo;

    private String bankAcronym;

    private String bankAccountNo;

    private String bankAccountName;

    private String bankReservedPhone;

    private String certNo;

    private CertType certType = CertType.ID_CARD;

    private CurrencyType currencyType = CurrencyType.CNY;

    @JsonFormat(pattern = ISO_DATE_TIME_PATTERN)
    @NotNull
    private LocalDateTime orderTime;

    private String callbackUrl;

    private String returnUrl;
}
