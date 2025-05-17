package pe.com.hacom.oms.adapter.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.hacom.oms.adapter.persistence.mongodb.document.OrderDocument;
import pe.com.hacom.oms.adapter.persistence.mongodb.repository.OrderRepository;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.application.port.out.OrderPersistence;
import pe.com.hacom.oms.adapter.mapper.OrderMapper;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistence {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Mono<Order> save(Order order) {
        OrderDocument orderDocument = orderMapper.toDocument(order);
        return orderRepository.save(orderDocument)
                .doOnSuccess(savedDoc -> log.info("Persisted order {}", savedDoc.getOrderId()))
                .map(orderMapper::toOrder);
    }

}
