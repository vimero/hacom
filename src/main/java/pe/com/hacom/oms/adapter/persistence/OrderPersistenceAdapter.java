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

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistence {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Mono<Order> save(Order order) {
        OrderDocument orderDocument = orderMapper.toDocument(order);

        return orderRepository.save(orderDocument)
                .flatMap(savedDoc -> {
                    savedDoc.setOrderId(savedDoc.get_id().toHexString());
                    return orderRepository.save(savedDoc);
                })
                .doOnSuccess(doc -> log.info("Persisted order with ID {}", doc.getOrderId()))
                .map(orderMapper::toOrder);
    }

    @Override
    public Mono<String> findStatusByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId)
                .map(OrderDocument::getStatus)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Pedido no encontrado")));
    }

    @Override
    public Mono<Long> countOrdersByDateRange(OffsetDateTime from, OffsetDateTime to) {
        return orderRepository.countByTsBetween(from, to);
    }

}
