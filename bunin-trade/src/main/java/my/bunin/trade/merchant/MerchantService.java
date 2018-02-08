package my.bunin.trade.merchant;

import my.bunin.util.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    private static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMQpNKFFJUNVvwtmuuiien5mCHe/rsxW6ikmw3h6c+JWHWv6o8nHOAinJTz06IPMXXI/a/1nJ1lsY/2DzjIOCs2NpphnObkpkRkEQBy+mQeD9JI+a2Fe2D0q1I9xNgjtBh22RJo61ebRzCVr3knWFErQFSNxUjgoZ2vJBWQ1i+u7AgMBAAECgYAdHX3sT0EKpNgoWbp1pmLGuV+Ztopmq00yMnNiQc/1e5FTt5ib7cU0COsOsyMtJ3uEkO6hvAUr0VoTWimUEGArLmVkyiNzwb2FqqIwHt3BpSyXkzbozdCgmh+f/lPwpuwOyLLzA3tGT46uGrvgxdiG7msZJk86vaNwkCnYXYY4QQJBAOqfsgy8eRs5GIAFLVozN4VuyDl+PFryJ+TNd198UAjyTv2jZzmKLXJgXHZNglVt3Hs8ojKkxb6nEKOl/FbsU5ECQQDWCGme0idNvEmj5yM6RnaqVc0cvWLuI+sgyoZ/xRVLrglNQsv8lgzrAEKdVPawQDFoWuTCKvRGvoarDih+88yLAkADJoHNifpWUz3w+iDRxT5JnGMz4m4NCpNejyO7NIAenqJQ5ZDNRP23O3QCYqgNXZxIS1giNfQxyyx+BgyNXj8BAkEAv8hh+/ILTdh9k+Q06K7i/dZMKIh68TA2K/fNdQfAAUQ9CYHlDrvVb22YQyoysdOTchKgEGw03n8yZGwXVUpX6QJBALNn7kj/fvMCzPZwAkRusLDpD8HOCRrSu7mgIfARDB9wFzsqKkx6FZfPJH0NhXbUlCIcf5ctdZrDCQrPWwk9Wx0=";
    private static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEKTShRSVDVb8LZrroonp+Zgh3v67MVuopJsN4enPiVh1r+qPJxzgIpyU89OiDzF1yP2v9ZydZbGP9g84yDgrNjaaYZzm5KZEZBEAcvpkHg/SSPmthXtg9KtSPcTYI7QYdtkSaOtXm0cwla95J1hRK0BUjcVI4KGdryQVkNYvruwIDAQAB";

    public MerchantSecret getSecret(String merchantNo) {

        MerchantSecret secret = new MerchantSecret();
        secret.setMerchantNo(merchantNo);
        secret.setBase64PrivateKey(RSA_PRIVATE_KEY);
        secret.setBase64PublicKey(RSA_PUBLIC_KEY);
        secret.setCipherAlgorithm(SecurityUtils.RSA);
        secret.setSignatureAlgorithm(SecurityUtils.SHA256_WITH_RSA);

        return secret;
    }
}
