package pe.com.hacom.oms.adapter.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import lombok.extern.slf4j.Slf4j;
import pe.com.hacom.oms.application.port.in.CreateOrderUseCase;
import pe.com.hacom.oms.grpc.OrderResponse;

@Slf4j
public class OrderProcessorActor extends AbstractActor {

    private final CreateOrderUseCase createOrderUseCase;

    public static Props props(CreateOrderUseCase createOrderUseCase) {
        return Props.create(OrderProcessorActor.class, () -> new OrderProcessorActor(createOrderUseCase));
    }

    public OrderProcessorActor(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ProcessOrderMessage.class, msg -> {
                    log.info("Procesando orden: {}", msg.order());

                    createOrderUseCase.createOrder(msg.order())
                            .subscribe(
                                    createdOrder -> {
                                        log.info("Orden procesada y persistida: {}", createdOrder.getOrderId());
                                        OrderResponse response = OrderResponse.newBuilder()
                                                .setOrderId(createdOrder.getOrderId())
                                                .setStatus("PROCESSED")
                                                .build();
                                        msg.responseObserver().onNext(response);
                                        msg.responseObserver().onCompleted();
                                    },
                                    error -> {
                                        log.error("Error al procesar la orden: {}", error.getMessage(), error);
                                        msg.responseObserver().onError(error);
                                    }
                            );
                })
                .build();
    }
}
