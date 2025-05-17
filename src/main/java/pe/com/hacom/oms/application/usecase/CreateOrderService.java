package pe.com.hacom.oms.application.usecase;

import lombok.AllArgsConstructor;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.application.port.in.CreateOrderUseCase;
import pe.com.hacom.oms.application.port.out.OrderPersistence;

@AllArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

    private final OrderPersistence orderPersistence;

    @Override
    public void createOrder(Order order) {
        orderPersistence.save(order);
    }

}