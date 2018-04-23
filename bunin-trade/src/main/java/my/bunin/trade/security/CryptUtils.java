package my.bunin.trade.security;

import my.bunin.trade.merchant.query.MerchantSecret;
import my.bunin.util.SecurityUtils;
import my.bunin.util.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.Strings;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;


public class CryptUtils {

  /**
   * generate crypt.
   */
  public static Crypt generate(String merchantNo, String message, String cryptKey,
                               String signature) {
    Crypt crypt = new Crypt();
    crypt.setMerchantNo(merchantNo);
    crypt.setMessage(message);
    crypt.setCryptKey(cryptKey);
    crypt.setSignature(signature);
    return crypt;
  }

  /**
   * verify and decrypt crypt.
   */
  public static Crypt verifyAndDecrypt(Crypt crypt, MerchantSecret secret)
      throws GeneralSecurityException {
    PublicKey publicKey = SecurityUtils.getPublicKey(secret.getCipherAlgorithm(),
        secret.getBase64PublicKey());
    String verifyData = Crypt.MERCHANT_NO + StringUtils.EQUAL
        + crypt.getMerchantNo() + StringUtils.AMPERSAND
        + Crypt.MESSAGE + StringUtils.EQUAL + crypt.getMessage() + StringUtils.AMPERSAND
        + Crypt.CRYPT_KEY + StringUtils.EQUAL + crypt.getCryptKey();

    boolean verify = SecurityUtils.verify(secret.getSignatureAlgorithm(), publicKey,
        Strings.toUTF8ByteArray(verifyData),
        Base64.decodeBase64(crypt.getSignature()));

    if (!verify) {
      throw new GeneralSecurityException(
          String.format("message verify failed, request: %s", crypt));
    }

    String decryptedCryptKey = Strings.fromUTF8ByteArray(
        SecurityUtils.decrypt(secret.getCipherAlgorithm(),
        publicKey, Base64.decodeBase64(crypt.getCryptKey())));

    SecretKey secretKey = SecurityUtils.getSecretKey(secret.getSecretAlgorithm(),
        decryptedCryptKey);
    String decryptedMessage = Strings.fromUTF8ByteArray(
        SecurityUtils.decrypt(secret.getSecretAlgorithm(),
        secretKey, Base64.decodeBase64(crypt.getMessage())));

    crypt.setMessage(decryptedMessage);

    return crypt;
  }

  /**
   * encrypt and sign.
   */
  public static Crypt encryptAndSign(String merchantNo, String data, MerchantSecret secret)
      throws GeneralSecurityException {

    PrivateKey privateKey = SecurityUtils.getPrivateKey(secret.getCipherAlgorithm(),
        secret.getBase64PrivateKey());
    SecretKey secretKey = SecurityUtils.getSecretKey(secret.getSecretAlgorithm(),
        secret.getBase64SecretKey());

    String base64EncryptData = Base64.encodeBase64String(
        SecurityUtils.encrypt(secret.getSecretAlgorithm(),
            secretKey, Strings.toUTF8ByteArray(data)));

    String base64EncryptCryptKey = Base64.encodeBase64String(
        SecurityUtils.encrypt(secret.getCipherAlgorithm(), privateKey,
            Strings.toUTF8ByteArray(secret.getBase64SecretKey())));

    String verifyData = Crypt.MERCHANT_NO + StringUtils.EQUAL
        + merchantNo + StringUtils.AMPERSAND
        + Crypt.MESSAGE + StringUtils.EQUAL + base64EncryptData + StringUtils.AMPERSAND
        + Crypt.CRYPT_KEY + StringUtils.EQUAL + base64EncryptCryptKey;

    String base64Signature = Base64.encodeBase64String(SecurityUtils.sign(
        secret.getSignatureAlgorithm(),
        privateKey, Strings.toUTF8ByteArray(verifyData)));

    Crypt crypt = new Crypt();
    crypt.setMerchantNo(merchantNo);
    crypt.setMessage(base64EncryptData);
    crypt.setCryptKey(base64EncryptCryptKey);
    crypt.setSignature(base64Signature);

    return crypt;
  }


}
