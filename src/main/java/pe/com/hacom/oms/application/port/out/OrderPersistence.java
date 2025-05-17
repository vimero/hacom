package pe.com.hacom.oms.application.port.out;

import pe.com.hacom.oms.application.domain.Order;
import reactor.core.publisher.Mono;

public interface OrderPersistence {

    Mono<Order> save(Order order);

}