package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;
import my.bunin.core.BankAcronym;
import my.bunin.core.CertType;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class AccessConfiguration {

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

  private Map<BankAcronym, String> bankNoMapper = new HashMap<>();
  private Map<CertType, String> certTypeMapper = new HashMap<>();

}
