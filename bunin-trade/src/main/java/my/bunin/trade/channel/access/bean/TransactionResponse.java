package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse extends Response {

  private Transaction transaction;

}
