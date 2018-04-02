package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest extends PaymentRequest {

    public TransactionRequest() {
        super(RequestType.TRANSACTION);
    }

}
