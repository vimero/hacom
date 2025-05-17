package pe.com.hacom.oms.application.port.in;

import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

public interface GetOrderUseCase {

    Mono<String> getStatus(String orderId);
    Mono<Long> countBetween(OffsetDateTime from, OffsetDateTime to);

}
