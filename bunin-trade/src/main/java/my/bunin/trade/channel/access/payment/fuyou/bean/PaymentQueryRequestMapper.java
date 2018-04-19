package my.bunin.trade.channel.access.payment.fuyou.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("SpellCheckingInspection")
@Getter
@Setter
@XStreamAlias("qrytransreq")
public class PaymentQueryRequestMapper {

    @XStreamAlias("ver")
    private String version;

    @XStreamAlias("busicd")
    private String businessCode;

    @XStreamAlias("orderno")
    private String orderNo;

    @XStreamAlias("startdt")
    private String startDate;

    @XStreamAlias("enddt")
    private String endDate;

    @XStreamAlias("transst")
    private String transactionStatus;

}
