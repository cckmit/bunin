package my.bunin.trade.order.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.CertType;
import my.bunin.core.ChannelType;
import my.bunin.core.IdentityStatus;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "channel_identity", uniqueConstraints = @UniqueConstraint(columnNames = {"identity_no", "channel_no"}))
public class ChannelIdentity {

    @Id
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
