package my.bunin.trade.channel.access.payment.fuyou.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("SpellCheckingInspection")
@Getter
@Setter
@XStreamAlias("trans")
public class TransMapper {

  @XStreamAlias("merdt")
  private String settledDate;

  @XStreamAlias("orderno")
  private String orderNo;

  @XStreamAlias("accntno")
  private String accountNo;

  @XStreamAlias("accntnm")
  private String accountName;

  @XStreamAlias("amt")
  private String amount;

  @XStreamAlias("entseq")
  private String enterpriseSequence;

  @XStreamAlias("memo")
  private String remark;

  @XStreamAlias("txncd")
  private String transactionCode;

  @XStreamAlias("projectid")
  private String projectId;

  @XStreamAlias("state")
  private String status;

  @XStreamAlias("result")
  private String message;

  @XStreamAlias("reason")
  private String reason;
}
