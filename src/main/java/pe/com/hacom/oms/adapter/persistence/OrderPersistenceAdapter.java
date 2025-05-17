package pe.com.hacom.oms.adapter.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.application.port.out.OrderPersistence;

@Slf4j
@Service
public class OrderPersistenceAdapter implements OrderPersistence {

    @Override
    public void save(Order order) {
        log.info("Persisting order {}", order.getOrderId());
    }

}
