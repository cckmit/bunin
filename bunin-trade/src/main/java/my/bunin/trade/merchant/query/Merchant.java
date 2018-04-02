package my.bunin.trade.merchant.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.MerchantStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "merchant", uniqueConstraints = @UniqueConstraint(columnNames = {"no"}))
public class Merchant {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Column(length = 64, nullable = false)
    private String no;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false)
    private MerchantStatus status;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Merchant parent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
    private Set<Merchant> merchants = new HashSet<>();

}
