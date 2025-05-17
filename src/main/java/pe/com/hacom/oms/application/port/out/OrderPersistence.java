package pe.com.hacom.oms.application.port.out;

import pe.com.hacom.oms.application.domain.Order;

public interface OrderPersistence {

    void save(Order order);

}