package pe.com.hacom.oms.application.usecase;

import lombok.RequiredArgsConstructor;
import pe.com.hacom.oms.application.port.in.GetOrderUseCase;
import pe.com.hacom.oms.application.port.out.OrderPersistence;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@RequiredArgsConstructor
public class GetOrderService implements GetOrderUseCase {

    private final OrderPersistence orderPersistence;

    @Override
    public Mono<String> getStatus(String orderId) {
        return orderPersistence.findStatusByOrderId(orderId);
    }

    @Override
    public Mono<Long> countBetween(OffsetDateTime from, OffsetDateTime to) {
        return orderPersistence.countOrdersByDateRange(from, to);
    }

}
