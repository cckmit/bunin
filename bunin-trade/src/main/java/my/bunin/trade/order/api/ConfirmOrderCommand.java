package my.bunin.trade.order.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ConfirmOrderCommand extends AbstractCommand {

    private String orderNo;
    private String merchantNo;
}
