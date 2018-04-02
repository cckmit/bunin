package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PaymentRequest extends Request {

    public PaymentRequest(RequestType type) {
        super(type);
    }

    private Transaction transaction;

}
