package my.bunin.trade.channel.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

@Setter
@Getter
@ToString
public class ChannelAccess {

    @Id
    private String channelNo;

    private String merchantNo;

    private String username;

    private String password;

    private String baseUrl;

    private String notificationUrl;

    private String returnUrl;

    private String base64PrivateKey;

    private String base64PrivateKeyPassword;

    private String base64PublicKey;

    private String cipherAlgorithm;

    private String signatureAlgorithm;
}
