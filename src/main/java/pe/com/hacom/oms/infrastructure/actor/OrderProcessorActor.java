package pe.com.hacom.oms.infrastructure.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.application.port.in.CreateOrderUseCase;
import pe.com.hacom.oms.grpc.OrderResponse;

@Slf4j
public class OrderProcessorActor extends AbstractActor {

    private final CreateOrderUseCase createOrderUseCase;

    public static Props props(CreateOrderUseCase orderPersistence) {
        return Props.create(OrderProcessorActor.class, () -> new OrderProcessorActor(orderPersistence));
    }

    public OrderProcessorActor(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    public record ProcessOrderMessage(Order order, StreamObserver<OrderResponse> observer) {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(ProcessOrderMessage.class, msg -> {
                log.info("Procesando orden: {}", msg.order);
                createOrderUseCase.createOrder(msg.order);
                log.info("Orden procesada y persistida: {}", msg.order.getOrderId());
                OrderResponse response = OrderResponse.newBuilder()
                    .setOrderId(msg.order.getOrderId())
                    .setStatus("PROCESSED")
                    .build();
                msg.observer.onNext(response);
                msg.observer.onCompleted();
            })
            .build();
    }

}
