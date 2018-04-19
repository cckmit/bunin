package my.bunin.trade.channel.access.payment.fuyou;

import lombok.extern.slf4j.Slf4j;
import my.bunin.core.*;
import my.bunin.trade.channel.access.bean.*;
import my.bunin.util.JacksonUtils;
import my.bunin.util.SecurityUtils;
import my.bunin.util.SnowFlake;
import my.bunin.util.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FuyouExecutorTest {


    private FuyouExecutor executor;

    private static final SnowFlake snowFlake = new SnowFlake(1L, 1L);

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
        configuration.setCertTypeMapper(generateCertTypeMapper());

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

    private Map<CertType, String> generateCertTypeMapper() {
        Map<CertType, String> certTypeMapper = new HashMap<>();
        certTypeMapper.put(CertType.ID_CARD, "0");
        certTypeMapper.put(CertType.PASSPORT, "1");
        certTypeMapper.put(CertType.RESIDENCE_BOOKLET, "5");
        certTypeMapper.put(CertType.ARMY_ID_CARD, "2");
        certTypeMapper.put(CertType.POLICE_ID_CARD, "7");
        certTypeMapper.put(CertType.SOLDIER_ID_CARD, "3");
        certTypeMapper.put(CertType.ALIEN_RESIDENCE_PERMIT, "7");
        certTypeMapper.put(CertType.MTP_HK_MACAO, "7");
        certTypeMapper.put(CertType.MTP_TAIWAN, "7");

        return certTypeMapper;
    }

    @Test
    public void testRecharge() throws IOException {

        PaymentRequest request = new PaymentRequest();
        request.setConfiguration(generateConfiguration());

        Transaction transaction = new Transaction();
        transaction.setOrderNo(String.valueOf(snowFlake.nextId()));
        transaction.setSerialNo(String.valueOf(snowFlake.nextId()));
        transaction.setTransactionType(TransactionType.RECHARGE);
        transaction.setPaymentType(PaymentType.FASTPAY);
        transaction.setStage(RequestStage.WHOLE);
        transaction.setAmount(BigDecimal.valueOf(5));
        transaction.setAccountType(AccountType.PRIVATE);
        transaction.setCurrencyType(CurrencyType.CNY);
        transaction.setExecuteTime(LocalDateTime.now());
        transaction.setCreateTime(LocalDateTime.now());
        transaction.setUpdateTime(LocalDateTime.now());
        transaction.setSettlementDate(LocalDate.now());

        transaction.setBankAcronym(BankAcronym.ICBC);
        transaction.setBankAccountName("张飞");
        transaction.setBankAccountNo("6227002942040548888");
        transaction.setBankReservedPhone("13521238888");
        transaction.setCertNo("372929199611108888");
        transaction.setCertType(CertType.ID_CARD);

        request.setTransaction(transaction);

        log.info("request: {}", JacksonUtils.writeValueAsString(request));

        PaymentResponse response = executor.execute(request);

        log.info("response: {}", JacksonUtils.writeValueAsString(response));

    }

    @Test
    public void testRechargeQuery() throws IOException {
        PaymentQueryRequest request = new PaymentQueryRequest();

        // 986852527347732481
        request.setConfiguration(generateConfiguration());

        Transaction transaction = new Transaction();
        transaction.setSerialNo("986852527347732481");
        transaction.setTransactionType(TransactionType.RECHARGE);
        transaction.setPaymentType(PaymentType.FASTPAY);
        transaction.setAmount(BigDecimal.valueOf(5));
        transaction.setAccountType(AccountType.PRIVATE);
        transaction.setCurrencyType(CurrencyType.CNY);

        request.setTransaction(transaction);

        log.info("request: {}", JacksonUtils.writeValueAsString(request));

        PaymentQueryResponse response = executor.execute(request);

        log.info("response: {}", JacksonUtils.writeValueAsString(response));


    }

}
