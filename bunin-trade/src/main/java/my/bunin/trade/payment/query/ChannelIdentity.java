package my.bunin.trade.payment.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.ChannelType;
import my.bunin.core.IdentityStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Getter
@Setter
@ToString
@Entity
@Table(name = "channel_identity",
    uniqueConstraints = @UniqueConstraint(columnNames = {"identity_no", "channel_no"}))
public class ChannelIdentity {

  @Id
  @GeneratedValue
  private Long id;

  @Version
  private Long version;

  @ManyToOne
  @JoinColumn(name = "identity_id", nullable = false)
  private Identity identity;

  @Column(name = "identity_no", length = 64, nullable = false)
  private String identityNo;

  @Column(name = "channel_no", length = 64, nullable = false)
  private String channelNo;

  @Enumerated(EnumType.STRING)
  @Column(name = "channel_type", length = 64, nullable = false)
  private ChannelType channelType;

  @Enumerated(EnumType.STRING)
  @Column(length = 64, nullable = false)
  private IdentityStatus status;
}
