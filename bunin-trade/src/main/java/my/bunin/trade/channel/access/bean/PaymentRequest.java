package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest extends TransactionRequest {

    public PaymentRequest() {
        super(RequestType.PAYMENT);
    }

}
