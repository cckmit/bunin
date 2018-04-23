package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustodyRegisterResponse extends TransactionResponse {

  private String userRole;
  private String authList;
  private String failTime;
  private String accessType;
  private BigDecimal amount;
}
