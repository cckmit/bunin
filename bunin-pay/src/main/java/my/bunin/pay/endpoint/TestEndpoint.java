package my.bunin.pay.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Slf4j
@Component
@Path("test")
public class TestEndpoint {

    @GET
    @Path("hi")
    public Response hi(@QueryParam("name") String name) {
        log.info("name's value: {}", name);
        String hiName = Optional.ofNullable(name).orElse("My Friend");
        return Response.ok(String.format("Hi, %s", hiName)).build();
    }
}
