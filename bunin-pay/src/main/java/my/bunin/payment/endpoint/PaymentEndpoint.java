package my.bunin.payment.endpoint;

import lombok.extern.slf4j.Slf4j;
import my.bunin.payment.endpoint.bean.Crypt;
import my.bunin.payment.security.SecurityHandler;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.GeneralSecurityException;

@Slf4j
@Component
@Path("payment")
public class PaymentEndpoint {

    private SecurityHandler securityHandler;

    public PaymentEndpoint(SecurityHandler securityHandler) {
        this.securityHandler = securityHandler;
    }

    @POST
    @Path("order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response order(@Valid Crypt crypt) throws GeneralSecurityException {
        log.info("order request: {}", crypt);
        Crypt decryptedData = securityHandler.verifyAndDecrypt(crypt, null);
        return Response.ok(decryptedData).build();
    }

    @POST
    @Path("gateway")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gateway(@QueryParam("merchantNo") @NotNull String merchantNo,
                            @QueryParam("message") @NotNull String message,
                            @QueryParam("signature") @NotNull String signature){
        Crypt crypt = new Crypt();
        crypt.setMerchantNo(merchantNo);
        crypt.setMessage(message);
        crypt.setSignature(signature);

        log.info("gateway request: {}", crypt);
        return Response.ok(crypt).build();
    }


}
