package pe.com.hacom.oms.application.port.in;

import pe.com.hacom.oms.application.domain.Order;
import reactor.core.publisher.Mono;

public interface CreateOrderUseCase {

    Mono<Order> createOrder(Order order);

}
