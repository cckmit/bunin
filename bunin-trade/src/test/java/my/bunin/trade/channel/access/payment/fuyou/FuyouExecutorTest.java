package my.bunin.trade.channel.access.payment.fuyou;

import lombok.extern.slf4j.Slf4j;
import my.bunin.core.BankAcronym;
import my.bunin.trade.channel.access.bean.AccessConfiguration;
import my.bunin.util.SecurityUtils;
import my.bunin.util.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FuyouExecutorTest {


    private FuyouExecutor executor;

    @Before
    public void init() throws Exception {
        executor = new FuyouExecutor(generateHttpClient());
    }

    private HttpClient generateHttpClient() throws Exception {
        return HttpClients
                .custom()
                .setDefaultRequestConfig(RequestConfig
                        .custom()
                        .setConnectTimeout(30000)
                        .setConnectionRequestTimeout(30000)
                        .setSocketTimeout(30000)
                        .build())
                .setSSLContext(SSLContextBuilder
                        .create()
                        .setProtocol("TLS")
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .setSecureRandom(new SecureRandom())
                        .build())
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
    }

    private AccessConfiguration generateConfiguration() {
        AccessConfiguration configuration = new AccessConfiguration();
        configuration.setMerchantNo("0002900F0345153");
        configuration.setPassword("123456");
        configuration.setBaseUrl("https://fht-test.fuiou.com/fuMer/req.do");
        configuration.setSignatureAlgorithm(SecurityUtils.MD5);
        configuration.setBankNoMapper(generateBankNoMapper());

        return configuration;
    }

    private Map<BankAcronym, String> generateBankNoMapper() {
        Map<BankAcronym, String> bankNoMapper = new HashMap<>();
        bankNoMapper.put(BankAcronym.ICBC, "0102");
        bankNoMapper.put(BankAcronym.ABC, "0103");
        bankNoMapper.put(BankAcronym.BOC, "0104");
        bankNoMapper.put(BankAcronym.CCB, "0105");
        bankNoMapper.put(BankAcronym.BOCOM, "0301");
        bankNoMapper.put(BankAcronym.CMB, "0308");
        bankNoMapper.put(BankAcronym.CIB, "0309");
        bankNoMapper.put(BankAcronym.CMBC, "0305");
        bankNoMapper.put(BankAcronym.CGB, "0306");
        bankNoMapper.put(BankAcronym.PAB, "0307");
        bankNoMapper.put(BankAcronym.SPDB, "0310");
        bankNoMapper.put(BankAcronym.HXB, "0304");
        bankNoMapper.put(BankAcronym.BOB, "0313");
        bankNoMapper.put(BankAcronym.BOSC, "0313");
        bankNoMapper.put(BankAcronym.HFB, "0315");
        bankNoMapper.put(BankAcronym.PSBC, "0403");
        bankNoMapper.put(BankAcronym.CEB, "0303");
        bankNoMapper.put(BankAcronym.CNCB, "0302");
        bankNoMapper.put(BankAcronym.CZB, "0316");
        bankNoMapper.put(BankAcronym.CBHB, "0318");
        bankNoMapper.put(BankAcronym.HSB, "0319");
        bankNoMapper.put(BankAcronym.SRCB, "0322");

//        其他农村商业银行 0314
//        村镇银行 0320
//        其他农村信用合作社 0402

        return bankNoMapper;
    }

    @Test
    public void testRecharge() {

    }

}
