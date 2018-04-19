package my.bunin.trade.channel.access.payment.fuyou;

import com.google.common.base.Joiner;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import my.bunin.core.PaymentType;
import my.bunin.core.TransactionStatus;
import my.bunin.core.TransactionType;
import my.bunin.trade.channel.access.AbstractExecutor;
import my.bunin.trade.channel.access.bean.*;
import my.bunin.trade.channel.access.payment.fuyou.bean.*;
import my.bunin.util.NumberUtils;
import my.bunin.util.SecurityUtils;
import my.bunin.util.StringUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.util.Strings;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static my.bunin.util.StringUtils.VERTICAL;

@Slf4j
public class FuyouExecutor extends AbstractExecutor {

    private static final String VER_1_0 = "1.0";

    private static final String RECHARGE_REQUEST_TYPE = "sincomeforreq";
    private static final String RECHARGE_QUERY_REQUEST_TYPE = "qrytransreq";
    private static final String RECHARGE_BUSINESS_CODE = "AC01";

    private static final String FIELD_XML = "xml";
    private static final String FIELD_MERCHANT_ID = "merid";
    private static final String FIELD_REQUEST_TYPE = "reqtype";
    private static final String FIELD_MAC = "mac";

    private static final String SUCCESS_CODES = "|000000|1|";

    private static final Header HEARDER_FORM_URL_ENCODED =
            new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

    private HttpClient client;

    private final XStream xStream;

    public FuyouExecutor(HttpClient client) {
        this.client = client;

        xStream = new XStream();
        xStream.processAnnotations(RechargePaymentRequestMapper.class);
        xStream.processAnnotations(RechargePaymentResponseMapper.class);
        xStream.processAnnotations(PaymentQueryRequestMapper.class);
        xStream.processAnnotations(PaymentQueryResponseMapper.class);

        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{
                RechargePaymentRequestMapper.class,
                RechargePaymentResponseMapper.class,
                PaymentQueryRequestMapper.class,
                PaymentQueryResponseMapper.class,
                TransMapper.class
        });
        xStream.ignoreUnknownElements();

    }

    @Override
    public <T extends Response> T execute(Request request) throws IOException {
        String requestContent = format(request);

        HttpResponse httpResponse = doExecute(request, requestContent);

        String responseContent = EntityUtils.toString(httpResponse.getEntity(), StringUtils.UTF8);

        //noinspection unchecked
        return (T)parse(request, responseContent);

    }

    private HttpResponse doExecute(Request request, String requestContent) throws IOException {
        HttpPost httpPost = new HttpPost(request.getConfiguration().getBaseUrl());
        httpPost.setEntity(new StringEntity(requestContent, StringUtils.UTF8));
        httpPost.setHeader(HEARDER_FORM_URL_ENCODED);
        return client.execute(httpPost);
    }

    @Override
    protected String format(PaymentRequest request) {
        Transaction transaction = request.getTransaction();

        if (TransactionType.RECHARGE.equals(transaction.getTransactionType())
                && PaymentType.FASTPAY.equals(transaction.getPaymentType())) {
            return formatRechargePaymentRequest(request);
        }

        throw new UnsupportedOperationException(String.format("%s transaction type was unsupported. payment type: %s",
                transaction.getTransactionType(), transaction.getPaymentType()));
    }

    private String formatRechargePaymentRequest(PaymentRequest request) {
        RechargePaymentRequestMapper requestMapper = new RechargePaymentRequestMapper();

        Transaction transaction = request.getTransaction();
        AccessConfiguration configuration = request.getConfiguration();

        requestMapper.setVersion(VER_1_0);
        requestMapper.setSettleDate(transaction.getSettlementDate().format(DateTimeFormatter.BASIC_ISO_DATE));
        requestMapper.setOrderNo(transaction.getSerialNo());
        requestMapper.setBankNo(configuration.getBankNoMapper().get(transaction.getBankAcronym()));

        requestMapper.setAccountNo(transaction.getBankAccountNo());
        requestMapper.setAccountName(transaction.getBankAccountName());
        requestMapper.setAmount(NumberUtils.removeDecimalPoint(transaction.getAmount()).toString());
        requestMapper.setCertNo(transaction.getCertNo());
        requestMapper.setCertType(configuration.getCertTypeMapper().get(transaction.getCertType()));
        requestMapper.setMobile(transaction.getBankReservedPhone());

        String xml = xStream.toXML(requestMapper);

        return buildRequestContent(request, xml, RECHARGE_REQUEST_TYPE);
    }

    @Override
    protected String format(PaymentQueryRequest request) {
        Transaction transaction = request.getTransaction();

        if (TransactionType.RECHARGE.equals(transaction.getTransactionType())
                && PaymentType.FASTPAY.equals(transaction.getPaymentType())) {
            return formatRechargePaymentQueryRequest(request);
        }

        throw new UnsupportedOperationException(String.format("%s transaction type was unsupported.",
                transaction.getTransactionType()));

    }

    private String formatRechargePaymentQueryRequest(PaymentQueryRequest request) {
        PaymentQueryRequestMapper requestMapper = new PaymentQueryRequestMapper();
        Transaction transaction = request.getTransaction();

        requestMapper.setVersion(VER_1_0);
        requestMapper.setBusinessCode(RECHARGE_BUSINESS_CODE);
        requestMapper.setOrderNo(transaction.getSerialNo());
        requestMapper.setStartDate(LocalDateTime.now().minusDays(14).format(DateTimeFormatter.BASIC_ISO_DATE));
        requestMapper.setEndDate(LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));

        String xml = xStream.toXML(requestMapper);

        return buildRequestContent(request, xml, RECHARGE_QUERY_REQUEST_TYPE);
    }

    private String buildRequestContent(Request request, String xml, String type) {
        AccessConfiguration configuration = request.getConfiguration();

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put(FIELD_XML, xml);
        requestMap.put(FIELD_MERCHANT_ID, configuration.getMerchantNo());
        requestMap.put(FIELD_REQUEST_TYPE, type);

        String plainMac = Joiner.on(VERTICAL).skipNulls().join(configuration.getMerchantNo(), configuration.getPassword(),
                requestMap.get(FIELD_REQUEST_TYPE), xml);

        String mac = Hex.encodeHexString(SecurityUtils.digest(configuration.getSignatureAlgorithm(),
                Strings.toUTF8ByteArray(plainMac)));
        requestMap.put(FIELD_MAC, mac);

        return StringUtils.pair(requestMap);
    }

    @Override
    protected PaymentResponse parse(PaymentRequest request, String responseContent) {

        RechargePaymentResponseMapper responseMapper = (RechargePaymentResponseMapper)xStream.fromXML(responseContent);

        PaymentResponse response = new PaymentResponse();
        response.setCode(responseMapper.getCode());
        response.setMessage(responseMapper.getMessage());
        response.setContent(responseContent);

        Transaction transaction = new Transaction();
        transaction.setStatus(parseTransactionStatus(responseMapper.getCode()));
        response.setTransaction(transaction);

        return response;
    }

    private TransactionStatus parseTransactionStatus(String code) {

        String formatCode = String.format("|%s|", code);
        if (SUCCESS_CODES.contains(formatCode)) {
            return TransactionStatus.SUCCEED;
        } else {
            return TransactionStatus.FAILED;
        }
    }

    @Override
    protected PaymentQueryResponse parse(PaymentQueryRequest request, String responseContent) {
        PaymentQueryResponseMapper responseMapper = (PaymentQueryResponseMapper)xStream.fromXML(responseContent);

        List<TransMapper> transMappers =  responseMapper.getTransactions();

        PaymentQueryResponse response = new PaymentQueryResponse();
        response.setCode(responseMapper.getCode());
        response.setMessage(responseMapper.getMessage());
        response.setContent(responseContent);

        Transaction transaction = new Transaction();
        transaction.setStatus(parseTransactionQueryStatus(transMappers.get(0).getStatus()));
        response.setTransaction(transaction);

        return response;
    }

    private TransactionStatus parseTransactionQueryStatus(String status) {
        switch (status) {
            case "1":
                return TransactionStatus.SUCCEED;
            case "2":
                return TransactionStatus.FAILED;
            case "0":
            case "3":
            case "7":
            default:
                return TransactionStatus.PROCESSING;
        }
    }
}
