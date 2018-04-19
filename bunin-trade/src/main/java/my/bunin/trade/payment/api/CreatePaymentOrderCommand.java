package my.bunin.trade.payment.api;

import lombok.*;
import my.bunin.core.*;
import my.bunin.trade.payment.query.PaymentTransaction;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
public class CreatePaymentOrderCommand extends AbstractPaymentOrderCommand{

}
