package pe.com.hacom.oms.adapter.rest;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pe.com.hacom.oms.application.port.in.GetOrderUseCase;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final GetOrderUseCase getOrderUseCase;


    @GetMapping("/{orderId}/status")
    public Mono<Map<String, String>> getOrderStatus(@PathVariable String orderId) {
        log.info("Get order status by order id {}", orderId);
        return getOrderUseCase.getStatus(orderId)
                .map(status -> Map.of("orderId", orderId, "status", status));
    }

    @GetMapping("/count")
    public Mono<Map<String, Long>> countOrders(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to
    ) {
        return getOrderUseCase.countBetween(from, to)
                .map(count -> Map.of("total", count));
    }
}
