package my.bunin.payment.endpoint;

import lombok.extern.slf4j.Slf4j;
import my.bunin.payment.security.Crypt;
import my.bunin.payment.security.SecurityHandler;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
