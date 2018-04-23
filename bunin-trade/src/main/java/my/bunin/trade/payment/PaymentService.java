package my.bunin.trade.payment;

import my.bunin.trade.payment.bean.PaymentOrderRequest;
import my.bunin.trade.security.Crypt;
import my.bunin.util.JacksonUtils;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {


  /**
   * create order.
   */
  public void order(Crypt crypt) {

    PaymentOrderRequest request = JacksonUtils.readValue(crypt.getMessage(),
        PaymentOrderRequest.class);
    checkRequest(crypt, request);

    // create payment

    // execute payment


  }

  private void checkRequest(Crypt crypt, PaymentOrderRequest request) {
    if (!crypt.getMerchantNo().equals(request.getMerchantNo())) {
      throw new IllegalArgumentException(String.format(
          "merchantNo %s is different from merchantNo %s in message!",
          crypt.getMerchantNo(), request.getMerchantNo()));
    }
  }


}
