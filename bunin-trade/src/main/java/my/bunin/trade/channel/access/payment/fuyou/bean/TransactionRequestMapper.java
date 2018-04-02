package my.bunin.trade.channel.access.payment.fuyou.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("SpellCheckingInspection")
@Getter
@Setter
@XStreamAlias("incomeforreq")
public class TransactionRequestMapper {

    @XStreamAlias("ver")
    private String version;

    @XStreamAlias("merdt")
    private String settleDate;

    @XStreamAlias("orderno")
    private String orderNo;

    @XStreamAlias("bankno")
    private String bankNo;

    @XStreamAlias("accntno")
    private String accountNo;

    @XStreamAlias("accntnm")
    private String accountName;

    @XStreamAlias("amt")
    private String amount;

    @XStreamAlias("mobile")
    private String mobile;

    @XStreamAlias("certtp")
    private String certType;

    @XStreamAlias("certno")
    private String certNo;

    @XStreamAlias("txncd")
    private String transactionCode;

    @XStreamAlias("projectid")
    private String projectId;

}
