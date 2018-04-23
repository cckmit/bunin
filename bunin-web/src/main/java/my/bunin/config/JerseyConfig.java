package my.bunin.config;

import my.bunin.endpoint.PaymentEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    register(PaymentEndpoint.class);
  }
}
