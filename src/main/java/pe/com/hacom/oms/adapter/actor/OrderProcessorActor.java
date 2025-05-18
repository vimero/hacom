package pe.com.hacom.oms.adapter.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.hacom.oms.application.port.in.CreateOrderUseCase;
import pe.com.hacom.oms.application.port.out.SmsSenderPort;
import pe.com.hacom.oms.grpc.OrderResponse;

@Slf4j
@RequiredArgsConstructor
public class OrderProcessorActor extends AbstractActor {

    private final CreateOrderUseCase createOrderUseCase;
    private final SmsSenderPort smsSender;

    public static Props props(CreateOrderUseCase createOrderUseCase, SmsSenderPort smsSender) {
        return Props.create(OrderProcessorActor.class, () -> new OrderProcessorActor(createOrderUseCase, smsSender));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ProcessOrderMessage.class, msg -> {
                    log.info("Procesando orden: {}", msg.order());

                    createOrderUseCase.createOrder(msg.order())
                            .subscribe(
                                    createdOrder -> {
                                        log.info("Orden procesada: {}", createdOrder.getOrderId());

                                        String message = "Your order " + createdOrder.getOrderId() + " has been processed";
                                        smsSender.sendSms(createdOrder.getPhoneNumber(), message);

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
