package my.bunin.endpoint;

import lombok.extern.slf4j.Slf4j;
import my.bunin.trade.security.Crypt;
import my.bunin.trade.security.SecurityHandler;
import my.bunin.trade.order.api.CreateOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.GeneralSecurityException;
import java.util.UUID;

@Slf4j
@Component
@Path("payment")
public class PaymentEndpoint {

    private CommandGateway gateway;
    private SecurityHandler securityHandler;

    public PaymentEndpoint(SecurityHandler securityHandler,
                           CommandGateway gateway) {
        this.securityHandler = securityHandler;
        this.gateway = gateway;
    }

    @POST
    @Path("createOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@Valid CreateOrderCommand command) {
        log.info("create order request: {}", command);

        new CreateOrderCommand(command.getOrderNo(), command.getMerchantNo(), command.getAmount());
        command.setId(UUID.randomUUID().toString());
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
        Crypt crypt = securityHandler.generate(merchantNo, message, cryptKey, signature);

        log.info("order request: {}", crypt);
        Crypt decryptedData = securityHandler.verifyAndDecrypt(crypt);
        return Response.ok(decryptedData).build();
    }

    @POST
    @Path("gateway")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gateway(@QueryParam("merchantNo") @NotEmpty String merchantNo,
                            @QueryParam("message") @NotEmpty String message,
                            @QueryParam("cryptKey") @NotEmpty String cryptKey,
                            @QueryParam("signature") @NotEmpty String signature){
        Crypt crypt = securityHandler.generate(merchantNo, message, cryptKey, signature);

        log.info("gateway request: {}", crypt);
        return Response.ok(crypt).build();
    }


}
