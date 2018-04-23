package my.bunin.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {

  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  public static final String DEFAULT_PROVIDER = BouncyCastleProvider.PROVIDER_NAME;
  public static final int DEFAULT_KEY_SIZE = 1024;
  public static final String DEFAULT_SECURE_RANDOM_ALGORITHM = "SHA1PRNG";

  public static final String DSA = "DSA";
  public static final String RSA = "RSA";
  public static final String AES = "AES";
  public static final String DES = "DES";

  public static final String MD5 = "MD5";
  public static final String SHA1 = "SHA1";
  public static final String SHA256_WITH_RSA = "SHA256withRSA";

  /**
   * generate key pair.
   */
  public static KeyPair generateKeyPair(String algorithm, int keySize,
                                        String provider) throws GeneralSecurityException {
    Preconditions.checkNotNull(algorithm);
    KeyPairGenerator generator = Strings.isNullOrEmpty(provider)
        ? KeyPairGenerator.getInstance(algorithm)
        : KeyPairGenerator.getInstance(algorithm, DEFAULT_PROVIDER);
    generator.initialize(keySize > 0 ? keySize : DEFAULT_KEY_SIZE,
        SecureRandom.getInstance(DEFAULT_SECURE_RANDOM_ALGORITHM));

    return generator.generateKeyPair();
  }

  /**
   * generate key pair.
   */
  public static KeyPair generateKeyPair(String algorithm) throws GeneralSecurityException {
    return generateKeyPair(algorithm, DEFAULT_KEY_SIZE, DEFAULT_PROVIDER);
  }

  /**
   * generate secret key.
   */
  public static SecretKey generateSecretKey(String algorithm, String provider, int keySize)
      throws GeneralSecurityException {
    KeyGenerator generator = Strings.isNullOrEmpty(provider)
        ? KeyGenerator.getInstance(algorithm)
        : KeyGenerator.getInstance(algorithm, DEFAULT_PROVIDER);
    if (keySize >= 128) {
      generator.init(keySize);
    }
    return generator.generateKey();
  }

  /**
   * generate secret key.
   */
  public static SecretKey generateSecretKey(String algorithm) throws GeneralSecurityException {
    return generateSecretKey(algorithm, DEFAULT_PROVIDER, 0);
  }

  /**
   * get private key.
   */
  public static PrivateKey getPrivateKey(String algorithm, String base64Key,
                                         String provider) throws GeneralSecurityException {
    Preconditions.checkNotNull(algorithm);
    Preconditions.checkNotNull(base64Key);
    KeyFactory keyFactory = Strings.isNullOrEmpty(provider)
        ? KeyFactory.getInstance(algorithm) : KeyFactory.getInstance(algorithm, provider);
    return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(base64Key)));
  }

  /**
   * get private key.
   */
  public static PrivateKey getPrivateKey(String algoithm, String base64Key)
      throws GeneralSecurityException {
    return getPrivateKey(algoithm, base64Key, DEFAULT_PROVIDER);
  }

  /**
   * get public key.
   */
  public static PublicKey getPublicKey(String algorithm, String base64Key,
                                       String provider) throws GeneralSecurityException {
    Preconditions.checkNotNull(algorithm);
    Preconditions.checkNotNull(base64Key);
    KeyFactory keyFactory = Strings.isNullOrEmpty(provider)
        ? KeyFactory.getInstance(algorithm) : KeyFactory.getInstance(algorithm, provider);
    return keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(base64Key)));
  }

  /**
   * get public key.
   */
  public static PublicKey getPublicKey(String algorithm, String base64Key)
      throws GeneralSecurityException {
    return getPublicKey(algorithm, base64Key, DEFAULT_PROVIDER);
  }

  /**
   * get secret key.
   */
  public static SecretKey getSecretKey(String algorithm, KeySpec keySpec, String provider)
      throws GeneralSecurityException {

    SecretKeyFactory secretKeyFactory = Strings.isNullOrEmpty(provider)
        ? SecretKeyFactory.getInstance(algorithm)
        : SecretKeyFactory.getInstance(algorithm, provider);
    return secretKeyFactory.generateSecret(keySpec);
  }

  /**
   * get secret key.
   */
  public static SecretKey getSecretKey(String algorithm, String base64Key)
      throws GeneralSecurityException {
    KeySpec keySpec = new SecretKeySpec(Base64.decodeBase64(base64Key), algorithm);
    return getSecretKey(algorithm, keySpec, DEFAULT_PROVIDER);
  }

  /**
   * get certificate.
   */
  public static Certificate getCertificate(String type, String base64Key,
                                           String provider) throws GeneralSecurityException {
    Preconditions.checkNotNull(type);
    Preconditions.checkNotNull(base64Key);
    CertificateFactory certificateFactory = Strings.isNullOrEmpty(provider)
        ? CertificateFactory.getInstance(type) : CertificateFactory.getInstance(type, provider);
    return certificateFactory.generateCertificate(
        new ByteArrayInputStream(Base64.decodeBase64(base64Key)));
  }

  /**
   * get key store..
   */
  public static KeyStore getKeyStore(String type, String base64Key, String password,
                                     String provider) throws GeneralSecurityException, IOException {
    Preconditions.checkNotNull(type);
    Preconditions.checkNotNull(base64Key);
    KeyStore keyStore = Strings.isNullOrEmpty(provider)
        ? KeyStore.getInstance(type) : KeyStore.getInstance(type, provider);
    char[] passwordChars = Strings.isNullOrEmpty(password) ? null : password.toCharArray();
    keyStore.load(new ByteArrayInputStream(Base64.decodeBase64(base64Key)), passwordChars);
    return keyStore;
  }

  /**
   * sign.
   */
  public static byte[] sign(String algorithm, PrivateKey key, byte[] data,
                            String provider) throws GeneralSecurityException {
    Preconditions.checkNotNull(algorithm);
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(data);
    Signature signature = Strings.isNullOrEmpty(provider)
        ? Signature.getInstance(algorithm) : Signature.getInstance(algorithm, provider);
    signature.initSign(key, SecureRandom.getInstance(DEFAULT_SECURE_RANDOM_ALGORITHM));
    signature.update(data);
    return signature.sign();
  }

  /**
   * sign.
   */
  public static byte[] sign(String algorithm, PrivateKey key, byte[] data)
      throws GeneralSecurityException {
    return sign(algorithm, key, data, DEFAULT_PROVIDER);
  }

  /**
   * verify.
   */
  public static boolean verify(String algorithm, PublicKey key, byte[] data,
                               byte[] signature, String provider) throws GeneralSecurityException {
    Preconditions.checkNotNull(algorithm);
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(data);

    Signature verifier = Strings.isNullOrEmpty(provider)
        ? Signature.getInstance(algorithm)
        : Signature.getInstance(algorithm, provider);
    verifier.initVerify(key);
    verifier.update(data);
    return verifier.verify(signature);
  }

  /**
   * verify.
   */
  public static boolean verify(String algorithm, PublicKey key, byte[] data,
                               byte[] signature) throws GeneralSecurityException {
    return verify(algorithm, key, data, signature, DEFAULT_PROVIDER);
  }

  /**
   * encrypt.
   */
  public static byte[] encrypt(String algorithm, Key key, byte[] data,
                               AlgorithmParameterSpec spec, String provider)
      throws GeneralSecurityException {
    Preconditions.checkNotNull(algorithm);
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(data);
    Cipher cipher = Strings.isNullOrEmpty(provider)
        ? Cipher.getInstance(algorithm) : Cipher.getInstance(algorithm, provider);
    if (Objects.isNull(spec)) {
      cipher.init(Cipher.ENCRYPT_MODE, key);
    } else {
      cipher.init(Cipher.ENCRYPT_MODE, key, spec);
    }
    return cipher.doFinal(data);
  }

  /**
   * encrypt.
   */
  public static byte[] encrypt(String algorithm, Key key, byte[] data)
      throws GeneralSecurityException {
    return encrypt(algorithm, key, data, null, DEFAULT_PROVIDER);
  }

  /**
   * decrypt.
   */
  public static byte[] decrypt(String algorithm, Key key, byte[] data,
                               AlgorithmParameterSpec spec, String provider)
      throws GeneralSecurityException {
    Preconditions.checkNotNull(algorithm);
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(data);
    Cipher cipher = Strings.isNullOrEmpty(provider)
        ? Cipher.getInstance(algorithm) : Cipher.getInstance(algorithm, provider);

    if (Objects.isNull(spec)) {
      cipher.init(Cipher.DECRYPT_MODE, key);
    } else {
      cipher.init(Cipher.DECRYPT_MODE, key, spec);
    }
    return cipher.doFinal(data);
  }

  /**
   * decrypt.
   */
  public static byte[] decrypt(String algorithm, Key key, byte[] data)
      throws GeneralSecurityException {
    return decrypt(algorithm, key, data, null, DEFAULT_PROVIDER);
  }

  /**
   * digest.
   */
  public static byte[] digest(String algorithm, byte[] data) throws SecurityException {
    return digest(algorithm, data, null);
  }

  /**
   * digest.
   */
  public static byte[] digest(String algorithm, byte[] data, byte[] salt) throws SecurityException {
    Preconditions.checkNotNull(algorithm);
    Preconditions.checkNotNull(data);

    try {
      MessageDigest digest = MessageDigest.getInstance(algorithm, DEFAULT_PROVIDER);
      if (salt != null) {
        digest.update(salt);
      }
      digest.update(data);
      return digest.digest();
    } catch (NoSuchProviderException | NoSuchAlgorithmException err) {
      throw new SecurityException("Error digesting the data", err);
    }
  }

  /**
   * digest with out provider..
   */
  public static byte[] digestWithNoProvider(String algorithm, byte[] data)
      throws SecurityException {
    return digestWithNoProvider(algorithm, data, null);
  }

  /**
   * digest with out provider..
   */
  public static byte[] digestWithNoProvider(String algorithm, byte[] data, byte[] salt)
      throws SecurityException {
    Preconditions.checkNotNull(algorithm);
    Preconditions.checkNotNull(data);

    try {
      MessageDigest digest = MessageDigest.getInstance(algorithm);
      if (salt != null) {
        digest.update(salt);
      }
      digest.update(data);
      return digest.digest();
    } catch (NoSuchAlgorithmException err) {
      throw new SecurityException("Error digesting the data", err);
    }
  }

}
