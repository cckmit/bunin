package my.bunin.trade.order.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.bunin.core.CertType;
import my.bunin.core.IdentityStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "identity", uniqueConstraints = @UniqueConstraint(columnNames = {"identity_no"}))
public class Identity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Column(name = "identity_no", length = 64, nullable = false)
    private String identityNo;

    @Column(name = "bank_acronym", length = 64, nullable = false)
    private String bankAcronym;

    @Column(name = "digested_bank_account_no", nullable = false)
    private String digestedBankAccountNo;

    @Column(name = "encrypted_bank_account_no", nullable = false)
    private String encryptedBankAccountNo;

    @Column(name = "masked_bank_account_no", length = 64, nullable = false)
    private String maskedBankAccountNo;

    @Column(name = "encrypted_bank_account_name")
    private String encryptedBankAccountName;

    @Column(name = "masked_bank_account_name")
    private String maskedBankAccountName;

    @Column(name = "encrypted_bank_reserved_phone")
    private String encryptedBankReservedPhone;

    @Column(name = "masked_bank_reserved_phone", length = 64)
    private String maskedBankReservedPhone;

    @Column(name = "encrypted_cert_no")
    private String encryptedCertNo;

    @Column(name = "masked_cert_no", length = 64)
    private String maskedCertNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "cert_type", length = 64, nullable = false)
    private transient CertType certType;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false)
    private IdentityStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "identity")
    private Set<ChannelIdentity> channelIdentities = new HashSet<>();
}
