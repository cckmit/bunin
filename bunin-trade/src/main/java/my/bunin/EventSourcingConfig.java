package my.bunin;

import my.bunin.trade.order.OrderAggregate;
import my.bunin.trade.order.OrderCommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class EventSourcingConfig {

    @Resource
    private EventBus eventBus;

    @Autowired
    @Bean
    public OrderCommandHandler orderCommandHandler(AxonConfiguration configuration) {
        return new OrderCommandHandler(configuration.repository(OrderAggregate.class), eventBus);
    }






}
