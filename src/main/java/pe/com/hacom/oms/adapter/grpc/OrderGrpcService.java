package pe.com.hacom.oms.adapter.grpc;

import akka.actor.ActorRef;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.hacom.oms.adapter.actor.ProcessOrderMessage;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.application.domain.OrderItem;
import pe.com.hacom.oms.grpc.OrderRequest;
import pe.com.hacom.oms.grpc.OrderResponse;
import pe.com.hacom.oms.grpc.OrderServiceGrpc;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderGrpcService extends OrderServiceGrpc.OrderServiceImplBase {

    private final ActorRef orderProcessorActor;

    @Override
    public void insertOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        log.info("Recibida solicitud de orden gRPC: {}", request);
        Order order = new Order(
                request.getOrderId(),
                request.getCustomerId(),
                request.getPhoneNumber(),
                request.getItemsList().stream()
                        .map(i -> new OrderItem(i.getItemId(), i.getQuantity()))
                        .toList()
        );

        ProcessOrderMessage message = new ProcessOrderMessage(order, responseObserver);
        orderProcessorActor.tell(message, ActorRef.noSender());
    }

}