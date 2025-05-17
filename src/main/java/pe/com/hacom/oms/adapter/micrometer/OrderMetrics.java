package pe.com.hacom.oms.adapter.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMetrics {

    private final MeterRegistry meterRegistry;
    private Counter orderCreatedCounter;

    @PostConstruct
    public void init() {
        this.orderCreatedCounter = Counter.builder("orders.created.total")
                .description("Total de órdenes creadas")
                .register(meterRegistry);
    }

    public void incrementOrderCreated() {
        orderCreatedCounter.increment();
    }

}