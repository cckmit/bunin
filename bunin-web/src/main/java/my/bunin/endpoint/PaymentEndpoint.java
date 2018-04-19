package my.bunin.endpoint;

import lombok.extern.slf4j.Slf4j;
import my.bunin.trade.merchant.MerchantService;
import my.bunin.trade.merchant.query.MerchantSecret;
import my.bunin.trade.order.api.ConfirmPaymentOrderCommand;
import my.bunin.trade.security.Crypt;
import my.bunin.trade.security.CryptUtils;
import my.bunin.trade.order.api.CreatePaymentOrderCommand;
import org.apache.commons.io.IOUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @POST
    @Path("createOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@Valid CreatePaymentOrderCommand command) {
        log.info("create order request: {}", command);

        command.setId(UUID.randomUUID().toString());
        gateway.send(command);
        return Response.ok("{\"code\":\"succeed\"}").build();
    }

    @POST
    @Path("confirmOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response confirmOrder(@Valid ConfirmPaymentOrderCommand command) {
        log.info("confirm order request: {}", command);
        gateway.send(command);
        return Response.ok("{\"code\":\"succeed\"}").build();
    }


    @POST
    @Path("order")
    @Produces(MediaType.APPLICATION_JSON)
    public Response order(@QueryParam("merchantNo") @NotEmpty String merchantNo,
                          @QueryParam("message") @NotEmpty String message,
                          @QueryParam("cryptKey") @NotEmpty String cryptKey,
                          @QueryParam("signature") @NotEmpty String signature) throws GeneralSecurityException {
        Crypt crypt = CryptUtils.generate(merchantNo, message, cryptKey, signature);

        log.info("order request: {}", crypt);
        MerchantSecret secret = merchantService.getSecret(merchantNo);
        Crypt decryptedData = CryptUtils.verifyAndDecrypt(crypt, secret);
        return Response.ok(decryptedData).build();
    }

    @POST
    @Path("gateway")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gateway(@QueryParam("merchantNo") @NotEmpty String merchantNo,
                            @QueryParam("message") @NotEmpty String message,
                            @QueryParam("cryptKey") @NotEmpty String cryptKey,
                            @QueryParam("signature") @NotEmpty String signature){
        Crypt crypt = CryptUtils.generate(merchantNo, message, cryptKey, signature);

        log.info("gateway request: {}", crypt);
        return Response.ok(crypt).build();
    }


}
