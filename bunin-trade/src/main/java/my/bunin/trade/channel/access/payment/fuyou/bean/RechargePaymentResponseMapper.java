package my.bunin.trade.channel.access.payment.fuyou.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("SpellCheckingInspection")
@Getter
@Setter
@XStreamAlias("incomeforrsp")
public class RechargePaymentResponseMapper {

    @XStreamAlias("ret")
    private String code;

    @XStreamAlias("memo")
    private String message;

}
