package my.bunin.trade.merchant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MerchantSecret {

    private String merchantNo;
    private String base64PrivateKey;
    private String base64PublicKey;
    private String base64SecretKey;
    private String cipherAlgorithm;
    private String secretAlgorithm;
    private String signatureAlgorithm;

}
