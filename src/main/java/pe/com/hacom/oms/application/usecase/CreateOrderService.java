package pe.com.hacom.oms.application.usecase;

import lombok.AllArgsConstructor;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.application.port.in.CreateOrderUseCase;
import pe.com.hacom.oms.application.port.out.OrderPersistence;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

    private final OrderPersistence orderPersistence;

    @Override
    public Mono<Order> createOrder(Order order) {
        return orderPersistence.save(order);
    }

}