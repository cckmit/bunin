package my.bunin.trade.order.query;

import org.springframework.stereotype.Component;

@Component
public class ChannelIdentityManager {

    private ChannelIdentityRepository repository;

    public ChannelIdentityManager(ChannelIdentityRepository repository) {
        this.repository = repository;
    }
}
