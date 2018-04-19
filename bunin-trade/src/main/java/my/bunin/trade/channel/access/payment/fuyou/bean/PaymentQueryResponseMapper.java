package my.bunin.trade.channel.access.payment.fuyou.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@Getter
@Setter
@XStreamAlias("qrytransrsp")
public class PaymentQueryResponseMapper {

    @XStreamAlias("ret")
    private String code;

    @XStreamAlias("memo")
    private String message;

    @XStreamImplicit
    private List<TransMapper> transactions = new ArrayList<>();
}
