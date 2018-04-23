package my.bunin.trade.payment;

import lombok.extern.slf4j.Slf4j;
import my.bunin.trade.payment.api.ExecutePaymentOrderCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;

@Slf4j
public class OrderCommandHandler {

  private Repository<OrderAggregate> repository;
  private EventBus eventBus;

  public OrderCommandHandler(Repository<OrderAggregate> repository, EventBus eventBus) {
    this.repository = repository;
    this.eventBus = eventBus;
  }

  @CommandHandler
  public void handle(ExecutePaymentOrderCommand command) {
    Aggregate<OrderAggregate> aggregate = repository.load(command.getId());
    aggregate.execute(orderAggregate -> orderAggregate.execute(command));
  }
}
