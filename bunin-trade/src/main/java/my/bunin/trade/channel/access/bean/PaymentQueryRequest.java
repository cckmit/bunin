package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentQueryRequest extends TransactionRequest {

    public PaymentQueryRequest() {
        super(RequestType.PAYMENT_QUERY);
    }
}
