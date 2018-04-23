package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustodyRegisterRequest extends TransactionRequest {

  public CustodyRegisterRequest() {
    super(RequestType.CUSTODY_REGISTER);
  }

  private String userRole;
  private String authList;
  private String failTime;
  private BigDecimal amount;


}
