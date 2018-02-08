package my.bunin.trade.merchant.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.MerchantStatus;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false)
    private MerchantStatus status;

}
