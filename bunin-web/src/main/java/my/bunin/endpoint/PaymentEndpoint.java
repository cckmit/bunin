package my.bunin.endpoint;

import lombok.extern.slf4j.Slf4j;
import my.bunin.trade.merchant.MerchantService;
import my.bunin.trade.merchant.query.MerchantSecret;
import my.bunin.trade.payment.api.ConfirmPaymentOrderCommand;
import my.bunin.trade.payment.api.CreatePaymentOrderCommand;
import my.bunin.trade.security.Crypt;
import my.bunin.trade.security.CryptUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Slf4j
@Component
@Path("payment")
public class PaymentEndpoint {

  private CommandGateway gateway;

  private MerchantService merchantService;

  public PaymentEndpoint(MerchantService merchantService,
                         CommandGateway gateway) {
    this.merchantService = merchantService;
    this.gateway = gateway;
  }

  /**
   * create order.
   */
  @POST
  @Path("createOrder")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createOrder(@Valid CreatePaymentOrderCommand command) {
    log.info("create payment request: {}", command);

    command.setId(UUID.randomUUID().toString());
    gateway.send(command);
    return Response.ok("{\"code\":\"succeed\"}").build();
  }

  /**
   * confirm order.
   */
  @POST
  @Path("confirmOrder")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response confirmOrder(@Valid ConfirmPaymentOrderCommand command) {
    log.info("confirm payment request: {}", command);
    gateway.send(command);
    return Response.ok("{\"code\":\"succeed\"}").build();
  }


  /**
   * payment order.
   */
  @POST
  @Path("payment")
  @Produces(MediaType.APPLICATION_JSON)
  public Response order(@QueryParam("merchantNo") @NotEmpty String merchantNo,
                        @QueryParam("message") @NotEmpty String message,
                        @QueryParam("cryptKey") @NotEmpty String cryptKey,
                        @QueryParam("signature") @NotEmpty String signature)
      throws GeneralSecurityException {
    Crypt crypt = CryptUtils.generate(merchantNo, message, cryptKey, signature);

    log.info("payment request: {}", crypt);
    MerchantSecret secret = merchantService.getSecret(merchantNo);
    Crypt decryptedData = CryptUtils.verifyAndDecrypt(crypt, secret);
    return Response.ok(decryptedData).build();
  }

  /**
   * gateway payment order.
   */
  @POST
  @Path("gateway")
  @Produces(MediaType.APPLICATION_JSON)
  public Response gateway(@QueryParam("merchantNo") @NotEmpty String merchantNo,
                          @QueryParam("message") @NotEmpty String message,
                          @QueryParam("cryptKey") @NotEmpty String cryptKey,
                          @QueryParam("signature") @NotEmpty String signature) {
    Crypt crypt = CryptUtils.generate(merchantNo, message, cryptKey, signature);

    log.info("gateway request: {}", crypt);
    return Response.ok(crypt).build();
  }


}
