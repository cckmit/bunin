package my.bunin.payment.security;

import lombok.extern.slf4j.Slf4j;
import my.bunin.merchant.MerchantSecret;
import my.bunin.util.SecurityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.security.GeneralSecurityException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityHandlerTest {

    private static final String MERCHANT_NO = "testMerNo1";
    private static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMQpNKFFJUNVvwtmuuiien5mCHe/rsxW6ikmw3h6c+JWHWv6o8nHOAinJTz06IPMXXI/a/1nJ1lsY/2DzjIOCs2NpphnObkpkRkEQBy+mQeD9JI+a2Fe2D0q1I9xNgjtBh22RJo61ebRzCVr3knWFErQFSNxUjgoZ2vJBWQ1i+u7AgMBAAECgYAdHX3sT0EKpNgoWbp1pmLGuV+Ztopmq00yMnNiQc/1e5FTt5ib7cU0COsOsyMtJ3uEkO6hvAUr0VoTWimUEGArLmVkyiNzwb2FqqIwHt3BpSyXkzbozdCgmh+f/lPwpuwOyLLzA3tGT46uGrvgxdiG7msZJk86vaNwkCnYXYY4QQJBAOqfsgy8eRs5GIAFLVozN4VuyDl+PFryJ+TNd198UAjyTv2jZzmKLXJgXHZNglVt3Hs8ojKkxb6nEKOl/FbsU5ECQQDWCGme0idNvEmj5yM6RnaqVc0cvWLuI+sgyoZ/xRVLrglNQsv8lgzrAEKdVPawQDFoWuTCKvRGvoarDih+88yLAkADJoHNifpWUz3w+iDRxT5JnGMz4m4NCpNejyO7NIAenqJQ5ZDNRP23O3QCYqgNXZxIS1giNfQxyyx+BgyNXj8BAkEAv8hh+/ILTdh9k+Q06K7i/dZMKIh68TA2K/fNdQfAAUQ9CYHlDrvVb22YQyoysdOTchKgEGw03n8yZGwXVUpX6QJBALNn7kj/fvMCzPZwAkRusLDpD8HOCRrSu7mgIfARDB9wFzsqKkx6FZfPJH0NhXbUlCIcf5ctdZrDCQrPWwk9Wx0=";
    private static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEKTShRSVDVb8LZrroonp+Zgh3v67MVuopJsN4enPiVh1r+qPJxzgIpyU89OiDzF1yP2v9ZydZbGP9g84yDgrNjaaYZzm5KZEZBEAcvpkHg/SSPmthXtg9KtSPcTYI7QYdtkSaOtXm0cwla95J1hRK0BUjcVI4KGdryQVkNYvruwIDAQAB";
//    private static final String AES_KEY = "vIb9FZ9xQOrZZEsKeSxkWMiMyeDImXGU";
    private static final String AES_KEY = "k0PnL+WBMBZje7ZFHV+u7Q==";
    private static final String DES_KEY = "mKidXZffp6g=";

    @Resource
    private SecurityHandler securityHandler;

    private MerchantSecret generateSecret() {
        MerchantSecret secret = new MerchantSecret();
        secret.setMerchantNo(MERCHANT_NO);
        secret.setBase64PrivateKey(RSA_PRIVATE_KEY);
        secret.setBase64PublicKey(RSA_PUBLIC_KEY);
        secret.setBase64SecretKey(DES_KEY);
        secret.setCipherAlgorithm(SecurityUtils.RSA);
        secret.setSecretAlgorithm(SecurityUtils.DES);
        secret.setSignatureAlgorithm(SecurityUtils.SHA256_WITH_RSA);
        return secret;
    }

    @Test
    public void testEncryptAndSign() throws GeneralSecurityException {

        MerchantSecret secret = generateSecret();

        String merchantNo = secret.getMerchantNo();
        String data = "{\"name\":\"Jessie\",\"age\":\"4\"}";
        Crypt crypt = securityHandler.encryptAndSign(merchantNo, data, secret);

        log.info("crypt data: {}", crypt);

        Crypt decryptData = securityHandler.verifyAndDecrypt(crypt, secret);

        log.info("decrypt data: {}", decryptData);

        Assert.assertEquals(decryptData.getMessage(), data);


    }
}
