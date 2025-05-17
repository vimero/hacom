package pe.com.hacom.oms.adapter.persistence;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.hacom.oms.adapter.persistence.mongodb.document.OrderDocument;
import pe.com.hacom.oms.adapter.persistence.mongodb.repository.OrderRepository;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.application.port.out.OrderPersistence;

@Slf4j
@Service
@AllArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistence {

    private final OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        OrderDocument orderDocument = new OrderDocument();
        orderRepository.save(orderDocument);
        log.info("Persisting order {}", order.getOrderId());
    }

}
