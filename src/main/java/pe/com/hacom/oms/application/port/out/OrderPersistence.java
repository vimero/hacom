package pe.com.hacom.oms.application.port.out;

import pe.com.hacom.oms.application.domain.Order;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

public interface OrderPersistence {

    Mono<Order> save(Order order);
    Mono<String> findStatusByOrderId(String orderId);
    Mono<Long> countOrdersByDateRange(OffsetDateTime from, OffsetDateTime to);
}