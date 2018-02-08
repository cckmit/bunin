package my.bunin.trade.merchant.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@ToString
public class MerchantSecret {

    @Id
    private String merchantNo;

    private String base64PrivateKey;

    private String base64PublicKey;

    private String base64SecretKey;

    private String cipherAlgorithm;

    private String secretAlgorithm;

    private String signatureAlgorithm;

}
