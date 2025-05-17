package pe.com.hacom.oms.application.port.in;

import pe.com.hacom.oms.application.domain.Order;

public interface CreateOrderUseCase {

    void createOrder(Order order);

}
