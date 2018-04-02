package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionQueryRequest extends PaymentRequest {

    public TransactionQueryRequest() {
        super(RequestType.TRANSACTION_QUERY);
    }
}
