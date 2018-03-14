package my.bunin.trade.order.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "identity_order", uniqueConstraints = @UniqueConstraint(columnNames = {"order_no", "merchant_no"}))
public class IdentityOrder {

    @Id
    private String id;

    @Version
    private Long version;

    @Column(name = "order_no", length = 64, nullable = false)
    private String orderNo;

    @Column(name = "merchant_no", length = 64, nullable = false)
    private String merchantNo;

    @Column(name = "channel_no", length = 64)
    private String channelNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type", length = 64)
    private ChannelType channelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_type", length = 64, nullable = false)
    private AuthenticationType authenticationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", length = 64, nullable = false)
    private PaymentType paymentType;

    @Column(name = "account_no", length = 64)
    private String accountNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 64, nullable = false)
    private AccountType accountType;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "execute_time")
    private LocalDateTime executeTime;

    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Column(name = "settlement_date", nullable = false)
    private LocalDate settlementDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false)
    private OrderStatus status;

    @Column
    private String code;

    @Column
    private String message;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private Set<IdentityTransaction> transactions = new HashSet<>();

    private transient String bankAcronym;

    private transient String bankAccountNo;

    private transient String bankAccountName;

    private transient String bankReservedPhone;

    private transient String certNo;

    private transient CertType certType;

    private transient String callbackUrl;

    private transient String returnUrl;
}
