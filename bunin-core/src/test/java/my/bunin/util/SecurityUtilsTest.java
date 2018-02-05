package my.bunin.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class SecurityUtilsTest {

    @Test
    public void testGenerateDsaKeyPair() throws GeneralSecurityException {
        KeyPair keyPair = SecurityUtils.generateKeyPair(SecurityUtils.DSA);
        String base64PrivateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String base64PublicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        log.info("\ndsa private key: {}\ndsa public key: {}", base64PrivateKey, base64PublicKey);
    }

    @Test
    public void testGenerateRsaKeyPair() throws GeneralSecurityException {
        KeyPair keyPair = SecurityUtils.generateKeyPair(SecurityUtils.RSA);
        String base64PrivateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String base64PublicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        log.info("\nrsa private key: {}\nrsa public key: {}", base64PrivateKey, base64PublicKey);
    }

    @Test
    public void testGenerateAesKey() throws GeneralSecurityException {
        SecretKey secretKey = SecurityUtils.generateSecretKey(SecurityUtils.AES);
        log.info("aes secret key: {}", Base64.getEncoder().encodeToString(secretKey.getEncoded()));
    }

    @Test
    public void testGetAesKey() throws GeneralSecurityException {
//        String base64AesKey = "vIb9FZ9xQOrZZEsKeSxkWMiMyeDImXGU";
        String base64AesKey = "k0PnL+WBMBZje7ZFHV+u7Q==";
        SecretKey secretKey = SecurityUtils.getSecretKey(SecurityUtils.AES, base64AesKey);
        log.info("aes secret key: {}", Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        Assert.assertEquals(Base64.getEncoder().encodeToString(secretKey.getEncoded()), base64AesKey);
    }

    @Test
    public void testGenerateDesKey() throws GeneralSecurityException {
        SecretKey secretKey = SecurityUtils.generateSecretKey(SecurityUtils.DES);
        log.info("des secret key: {}", Base64.getEncoder().encodeToString(secretKey.getEncoded()));
    }

    @Test
    public void testGetDesKey() throws GeneralSecurityException {
        String base64AesKey = "mKidXZffp6g=";
        SecretKey secretKey = SecurityUtils.getSecretKey(SecurityUtils.DES, base64AesKey);
        log.info("aes secret key: {}", Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        Assert.assertEquals(Base64.getEncoder().encodeToString(secretKey.getEncoded()), base64AesKey);
    }


    @Test
    public void testDSA() throws GeneralSecurityException {

        String dsaBase64PrivateKey = "MIIBSAIBADCCASkGByqGSM44BAEwggEcAoGBAJT5o82CrdLd7LwFH/Ejf7X/rRlY9+u64k19tGmlp7PcOAPD7JCNgqfUQydAccitk/VxrNsPXkYGtGM6rowMy2Qr//B8EC3pXg4MPTF7kMC992VX6wJ4rSRFnX0+o2fjW/xmCwlSKxNZ1I+E6JLwK/0mN/txzpl+Gue4wXfyMGN/AhUA2NTPlRz34GtOgD+bS5MJMjoetZUCf0aw95G4U5ADk6FlxJKw+B56V4+oPrdYDkqrdgH3A0thSgbw3v/qNcWzb+nq6qOzC4oefxfAxZpjDENMsmRFYPyZ8DESqdM/eEm89jB8gR2yF75Sj5QHt8g2sewVSXIbFixN+ZVabe8VY7YHeQ5RiY1zHA3fKZ2puTV91QOjUbsEFgIUIRTUVMjqvWj1vRlUmu5HntUqn84=";
        String dsaBase64PublicKey = "MIIBtDCCASkGByqGSM44BAEwggEcAoGBAJT5o82CrdLd7LwFH/Ejf7X/rRlY9+u64k19tGmlp7PcOAPD7JCNgqfUQydAccitk/VxrNsPXkYGtGM6rowMy2Qr//B8EC3pXg4MPTF7kMC992VX6wJ4rSRFnX0+o2fjW/xmCwlSKxNZ1I+E6JLwK/0mN/txzpl+Gue4wXfyMGN/AhUA2NTPlRz34GtOgD+bS5MJMjoetZUCf0aw95G4U5ADk6FlxJKw+B56V4+oPrdYDkqrdgH3A0thSgbw3v/qNcWzb+nq6qOzC4oefxfAxZpjDENMsmRFYPyZ8DESqdM/eEm89jB8gR2yF75Sj5QHt8g2sewVSXIbFixN+ZVabe8VY7YHeQ5RiY1zHA3fKZ2puTV91QOjUbsDgYQAAoGAYPMJrDSWGT611sEbJa5KwH6Jbb8dMaV15y5VZiVSGNEqsw4BHUCLVn2akA4pK8oN5agxDfhq3t3ETePMa0FfhCjQX5dY9xGjwqZqTnWkP6VHfwEEYllik/3HFA9rlPG29CcD6ZsIssvKJkLUu+cLJo5YQuP4wmwN0JWcMgtyOxc=";

        final String message = getFixedMessage(2048, 'a');
        log.info("testing dsa case, original message: {}", message);

        byte[] signature = SecurityUtils.sign(SecurityUtils.DSA, SecurityUtils.getPrivateKey(
                SecurityUtils.DSA, dsaBase64PrivateKey, BouncyCastleProvider.PROVIDER_NAME),
                org.apache.commons.codec.binary.StringUtils.getBytesUtf8(message));
        log.info("testing dsa case, signature: {}", Base64.getEncoder().encodeToString(signature));

        boolean verify = SecurityUtils.verify(SecurityUtils.DSA, SecurityUtils.getPublicKey(
                SecurityUtils.DSA, dsaBase64PublicKey, BouncyCastleProvider.PROVIDER_NAME),
                org.apache.commons.codec.binary.StringUtils.getBytesUtf8(message), signature);

        assertThat(verify, is(true));
    }

    @Test
    public void testRSA() throws GeneralSecurityException {
        String base64PrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMoiVo8c5uImqFTq7qbCIzNq+9bhwf30CRhayERcgxUSZkyOfk2VlNqVRnebg9FzX/OT9oXXve7zAFsKV/Y2yOGlROnJ30nJlgpbK2HS0gxQwg9TvUJlxhIFTIiW8+MsgN9wL7kMH19IxVLm09zIuhiEnWYI3vMRyPNVKFsz5uNPAgMBAAECgYA5b3RlCfNZA18FchQJ8lQKQjX5IwD6ZiNHdlQ9iIP+stG6oyqkZQJ88bmqNthH5Z64Ga0M7vabNRU+yTuhTIPbiMJTgt9D13czhcc/FTLP2URTH5ZKKPMmGze7/2a+hJDvAGrhgAyvFDrCyRFmjhtgJnTLd+5i9FfM17mmQUgsoQJBAOqVdADyKyS6iwmiONsPEDnMNF8mHLqq+LWZSQXG0Ly/7pTMgZZJCJM4k+5/WbFmZD/RcldmMd2zt/x6jwRsmxcCQQDcln3TAYnHinxAzwLo1WtGmxBEqX1Gn5tv5fVtOxFYo3IBxXSDMm3m4HrXY6qpiv3mpJvIpsShVcy+PH5dFbyJAkEAm9yhLt+4erbXGpeGX0Yq6bwcL/wKqpxek4o9UnE+z6pWwtb+YvQzll3JLHXBCnWVtjFbX2avSzbV0BM+YxomEQJAdjSSwnLNkUctpFEKPxi2fsRzaEfm4OSAl+sDpIAFoJkda8OS1wc8C395dFhtSKM5wdGtxU1Qix/+MmcaU+lk+QJAOz6d88r2NmhMgXVt9xKlqpfQqUT7TBCCfvuoMPqppGHBW3BGz4D/5VWsGcZWGbm/pvgaWcsuPIR2HZsZ+sGXww==";
        String base64PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKIlaPHObiJqhU6u6mwiMzavvW4cH99AkYWshEXIMVEmZMjn5NlZTalUZ3m4PRc1/zk/aF173u8wBbClf2NsjhpUTpyd9JyZYKWyth0tIMUMIPU71CZcYSBUyIlvPjLIDfcC+5DB9fSMVS5tPcyLoYhJ1mCN7zEcjzVShbM+bjTwIDAQAB";

        final String message = getFixedMessage(12, 'b');
        log.info("testing rsa case, original message: {}", message);

        byte[] encryptBytes = SecurityUtils.encrypt(SecurityUtils.RSA, SecurityUtils.getPublicKey(
                SecurityUtils.RSA, base64PublicKey, BouncyCastleProvider.PROVIDER_NAME),
                org.apache.commons.codec.binary.StringUtils.getBytesUtf8(message));
        log.info("testing rsa case, encrypt message: {}", Base64.getEncoder().encodeToString(encryptBytes));

        byte[] decryptBytes = SecurityUtils.decrypt(SecurityUtils.RSA, SecurityUtils.getPrivateKey(
                SecurityUtils.RSA, base64PrivateKey, BouncyCastleProvider.PROVIDER_NAME), encryptBytes);
        assertThat(message, is(org.apache.commons.codec.binary.StringUtils.newStringUtf8(decryptBytes)));

        byte[] encryptBytes1 = SecurityUtils.encrypt(SecurityUtils.RSA, SecurityUtils.getPrivateKey(
                SecurityUtils.RSA, base64PrivateKey, BouncyCastleProvider.PROVIDER_NAME),
                org.apache.commons.codec.binary.StringUtils.getBytesUtf8(message));
        log.info("testing rsa case, encrypt message: {}", Base64.getEncoder().encodeToString(encryptBytes1));

        byte[] decryptBytes1 = SecurityUtils.decrypt(SecurityUtils.RSA, SecurityUtils.getPublicKey(
                SecurityUtils.RSA, base64PublicKey, BouncyCastleProvider.PROVIDER_NAME), encryptBytes1);
        assertThat(message, is(org.apache.commons.codec.binary.StringUtils.newStringUtf8(decryptBytes1)));

        byte[] signature = SecurityUtils.sign(SecurityUtils.RSA, SecurityUtils.getPrivateKey(
                SecurityUtils.RSA, base64PrivateKey, BouncyCastleProvider.PROVIDER_NAME),
                org.apache.commons.codec.binary.StringUtils.getBytesUtf8(message));
        log.info("testing rsa case, signature: {}", Base64.getEncoder().encodeToString(signature));

        boolean verify = SecurityUtils.verify(SecurityUtils.RSA, SecurityUtils.getPublicKey(
                SecurityUtils.RSA, base64PublicKey, BouncyCastleProvider.PROVIDER_NAME),
                org.apache.commons.codec.binary.StringUtils.getBytesUtf8(message), signature);
        assertThat(verify, is(true));
    }

    @Test
    public void testAES() throws GeneralSecurityException {

        String base64Key = "k0PnL+WBMBZje7ZFHV+u7Q==";
//        String base64Key = "mNrbLTJA0feY3+yLhfvenycjYWSHZykm"; // aes key size can not larger than 128 by default jre

        String message = getFixedMessage(512, 'c');
        log.info("testing aes case, original message: {}", message);

        byte[] encryptBytes = SecurityUtils.encrypt(SecurityUtils.AES,
                SecurityUtils.getSecretKey(SecurityUtils.AES, base64Key), org.apache.commons.codec.binary.StringUtils.getBytesUtf8(message));
        log.info("testing aes case, encrypt message: {}", Base64.getEncoder().encodeToString(encryptBytes));

        byte[] decryptBytes = SecurityUtils.decrypt(SecurityUtils.AES,
                SecurityUtils.getSecretKey(SecurityUtils.AES, base64Key), encryptBytes);
        assertThat(message, is(org.apache.commons.codec.binary.StringUtils.newStringUtf8(decryptBytes)));
    }

    @Test
    public void testDES() throws GeneralSecurityException {
//        String base64Key = "42Q0a7kQEBo=";
        String base64Key = "mKidXZffp6g=";

        String message = getFixedMessage(512, 'c');
        log.info("testing des case, original message: {}", message);

        byte[] encryptBytes = SecurityUtils.encrypt(SecurityUtils.DES,
                SecurityUtils.getSecretKey(SecurityUtils.DES, base64Key), org.apache.commons.codec.binary.StringUtils.getBytesUtf8(message));
        log.info("testing des case, encrypt message: {}", Base64.getEncoder().encodeToString(encryptBytes));

        byte[] decryptBytes = SecurityUtils.decrypt(SecurityUtils.DES,
                SecurityUtils.getSecretKey(SecurityUtils.DES, base64Key), encryptBytes);
        assertThat(message, is(org.apache.commons.codec.binary.StringUtils.newStringUtf8(decryptBytes)));
    }

    private static void printSet(
            String setName,
            Set algorithms) {
        System.out.println(setName + ":");

        if (algorithms.isEmpty()) {
            System.out.println("            None available.");
        } else {

            for (Object algorithm : algorithms) {
                String name = (String) algorithm;

                System.out.println("            " + name);
            }
        }
    }

    @Test
    public void testListAlgorithms() {
        Provider[] providers = Security.getProviders();
        Set<String> ciphers = new HashSet<>();
        Set<String> keyAgreements = new HashSet<>();
        Set<String> macs = new HashSet<>();
        Set<String> messageDigests = new HashSet<>();
        Set<String> signatures = new HashSet<>();

        for (int i = 0; i != providers.length; i++) {

            for (Object o : providers[i].keySet()) {
                String entry = (String) o;

                if (entry.startsWith("Alg.Alias.")) {
                    entry = entry.substring("Alg.Alias.".length());
                }

                if (entry.startsWith("Cipher.")) {
                    ciphers.add(entry.substring("Cipher.".length()));
                } else if (entry.startsWith("KeyAgreement.")) {
                    keyAgreements.add(entry.substring("KeyAgreement.".length()));
                } else if (entry.startsWith("Mac.")) {
                    macs.add(entry.substring("Mac.".length()));
                } else if (entry.startsWith("MessageDigest.")) {
                    messageDigests.add(entry.substring("MessageDigest.".length()));
                } else if (entry.startsWith("Signature.")) {
                    signatures.add(entry.substring("Signature.".length()));
                }
            }
        }

        printSet("Ciphers", ciphers);
        printSet("KeyAgreeents", keyAgreements);
        printSet("Macs", macs);
        printSet("MessageDigests", messageDigests);
        printSet("Signatures", signatures);
    }

    @Test
    public void testListBCCapabilities() {
        Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);

        for (Object o : provider.keySet()) {
            String entry = (String) o;

            // this indicates the entry refers to another entry

            if (entry.startsWith("Alg.Alias.")) {
                entry = entry.substring("Alg.Alias.".length());
            }

            String factoryClass = entry.substring(0, entry.indexOf('.'));
            String name = entry.substring(factoryClass.length() + 1);

            System.out.println(factoryClass + ": " + name);
        }
    }

    @Test
    public void testListProviders() {
        Provider[] providers = Security.getProviders();

        for (int i = 0; i != providers.length; i++) {
            System.out.println("Name: " + providers[i].getName() + " Version: " + providers[i].getVersion());
        }
    }

    @Test
    public void testPolicy() {
        byte[] data = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

        SecretKeySpec key64 = new SecretKeySpec(
                new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07},
                "Blowfish");

        try {
            Cipher c = Cipher.getInstance("Blowfish/ECB/NoPadding");

            c.init(Cipher.ENCRYPT_MODE, key64);

            c.doFinal(data);

            System.out.println("64 bit test: passed");
        } catch (SecurityException e) {
            if (Objects.equals(e.getMessage(), "Unsupported keysize or algorithm parameters")) {
                System.out.println("64 bit test failed: unrestricted policy files have not been installed for this runtime.");
            } else {
                System.err.println("64 bit test failed: there are bigger problems than just policy files: " + e);
            }
        } catch (GeneralSecurityException e) {
            System.err.println("64 bit test failed: there are bigger problems than just policy files: " + e);
        }

        SecretKeySpec key192 = new SecretKeySpec(
                new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                        0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                        0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17},
                "Blowfish");

        try {
            Cipher c = Cipher.getInstance("Blowfish/ECB/NoPadding");

            c.init(Cipher.ENCRYPT_MODE, key192);

            c.doFinal(data);

            System.out.println("192 bit test: passed");
        } catch (SecurityException e) {
            if (Objects.equals(e.getMessage(), "Unsupported keysize or algorithm parameters")) {
                System.out.println("192 bit test failed: unrestricted policy files have not been installed for this runtime.");
            } else {
                System.err.println("192 bit test failed: there are bigger problems than just policy files: " + e);
            }
        } catch (GeneralSecurityException e) {
            System.err.println("192 bit test failed: there are bigger problems than just policy files: " + e);
        }

        System.out.println("Tests completed");
    }

    @Test
    public void testPrecedence() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");

        System.out.println(cipher.getProvider());

        cipher = Cipher.getInstance("Blowfish/ECB/NoPadding", BouncyCastleProvider.PROVIDER_NAME);

        System.out.println(cipher.getProvider());
    }

//    @Test
//    public void testSimplePolicy() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//        byte[] data = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};
//
//        // create a 64 bit secret key from raw bytes
//
//        SecretKey key64 = new SecretKeySpec(new byte[]{0x00, 0x01, 0x02,
//                0x03, 0x04, 0x05, 0x06, 0x07}, "Blowfish");
//
//        // create a cipher and attempt to encrypt the data block with our key
//
//        Cipher c = Cipher.getInstance("Blowfish/ECB/NoPadding");
//
//        c.init(Cipher.ENCRYPT_MODE, key64);
//        c.doFinal(data);
//        System.out.println("64 bit test: passed");
//
//        // create a 192 bit secret key from raw bytes
//
//        SecretKey key192 = new SecretKeySpec(new byte[]{0x00, 0x01, 0x02,
//                0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c,
//                0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16,
//                0x17}, "Blowfish");
//
//        // now try encrypting with the larger key
//
//        c.init(Cipher.ENCRYPT_MODE, key192);
//        c.doFinal(data);
//        System.out.println("192 bit test: passed");
//
//        System.out.println("Tests completed");
//    }

    @Test
    public void testBasicDSA() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "BC");

        keyGen.initialize(512, new SecureRandom());

        KeyPair keyPair = keyGen.generateKeyPair();
        Signature signature = Signature.getInstance("DSA", "BC");

        // generate a signature
        signature.initSign(keyPair.getPrivate(), SecureRandom.getInstance("SHA1PRNG"));

//        byte[] message = new byte[]{(byte) 'a', (byte) 'b', (byte) 'c'};
        byte[] message = StringUtils.getBytesUtf8(getFixedMessage(1024, 'a'));


        signature.update(message);

        byte[] sigBytes = signature.sign();

        System.out.println("signature: " + Base64.getEncoder().encodeToString(sigBytes));

        // verify a signature
        signature.initVerify(keyPair.getPublic());

        signature.update(message);

        if (signature.verify(sigBytes)) {
            System.out.println("signature verification succeeded.");
        } else {
            System.out.println("signature verification failed.");
        }
    }

    private static String getFixedMessage(int length, char c) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(c);
        }
        return builder.toString();
    }
}
