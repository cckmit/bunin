package my.bunin.trade.channel.access.payment.fuyou.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("SpellCheckingInspection")
@Getter
@Setter
@XStreamAlias("qrytransrsp")
public class TransactionQueryResponseMapper {

    @XStreamAlias("ret")
    private String code;

    @XStreamAlias("memo")
    private String message;

    @XStreamImplicit
    private TransMapper transaction;
}