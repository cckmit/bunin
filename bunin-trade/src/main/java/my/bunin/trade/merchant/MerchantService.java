package my.bunin.trade.merchant;

import my.bunin.trade.merchant.query.MerchantSecret;
import my.bunin.util.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

  private static final String RSA_PRIVATE_KEY =
      "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMQpNKFF"
          + "JUNVvwtmuuiien5mCHe/rsxW6ikmw3h6c+JWHWv6o8nHOAinJTz0"
          + "6IPMXXI/a/1nJ1lsY/2DzjIOCs2NpphnObkpkRkEQBy+mQeD9JI+"
          + "a2Fe2D0q1I9xNgjtBh22RJo61ebRzCVr3knWFErQFSNxUjgoZ2vJ"
          + "BWQ1i+u7AgMBAAECgYAdHX3sT0EKpNgoWbp1pmLGuV+Ztopmq00y"
          + "MnNiQc/1e5FTt5ib7cU0COsOsyMtJ3uEkO6hvAUr0VoTWimUEGAr"
          + "LmVkyiNzwb2FqqIwHt3BpSyXkzbozdCgmh+f/lPwpuwOyLLzA3tG"
          + "T46uGrvgxdiG7msZJk86vaNwkCnYXYY4QQJBAOqfsgy8eRs5GIAF"
          + "LVozN4VuyDl+PFryJ+TNd198UAjyTv2jZzmKLXJgXHZNglVt3Hs8"
          + "ojKkxb6nEKOl/FbsU5ECQQDWCGme0idNvEmj5yM6RnaqVc0cvWLu"
          + "I+sgyoZ/xRVLrglNQsv8lgzrAEKdVPawQDFoWuTCKvRGvoarDih+"
          + "88yLAkADJoHNifpWUz3w+iDRxT5JnGMz4m4NCpNejyO7NIAenqJQ"
          + "5ZDNRP23O3QCYqgNXZxIS1giNfQxyyx+BgyNXj8BAkEAv8hh+/IL"
          + "Tdh9k+Q06K7i/dZMKIh68TA2K/fNdQfAAUQ9CYHlDrvVb22YQyoy"
          + "sdOTchKgEGw03n8yZGwXVUpX6QJBALNn7kj/fvMCzPZwAkRusLDp"
          + "D8HOCRrSu7mgIfARDB9wFzsqKkx6FZfPJH0NhXbUlCIcf5ctdZrD"
          + "CQrPWwk9Wx0=";
  private static final String RSA_PUBLIC_KEY =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEKTShRSVDVb8LZrroo"
          + "np+Zgh3v67MVuopJsN4enPiVh1r+qPJxzgIpyU89OiDzF1yP2v9Zy"
          + "dZbGP9g84yDgrNjaaYZzm5KZEZBEAcvpkHg/SSPmthXtg9KtSPcTY"
          + "I7QYdtkSaOtXm0cwla95J1hRK0BUjcVI4KGdryQVkNYvruwIDAQAB";

  /**
   * get secret.
   */
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
