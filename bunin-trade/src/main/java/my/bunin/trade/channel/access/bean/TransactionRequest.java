package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TransactionRequest extends Request {


  private Transaction transaction;

  public TransactionRequest(RequestType type) {
    super(type);
  }


}
