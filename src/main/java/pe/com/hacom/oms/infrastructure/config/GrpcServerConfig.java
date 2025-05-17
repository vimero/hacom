package pe.com.hacom.oms.infrastructure.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.application.domain.OrderItem;
import pe.com.hacom.oms.application.port.in.CreateOrderUseCase;
import pe.com.hacom.oms.grpc.OrderRequest;
import pe.com.hacom.oms.grpc.OrderResponse;
import pe.com.hacom.oms.grpc.OrderServiceGrpc;
import pe.com.hacom.oms.infrastructure.actor.OrderProcessorActor;

import java.io.IOException;

@Slf4j
@Configuration
public class GrpcServerConfig {

    @Value("${grcpPort}")
    private int grcpPort;

    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("order-system");
    }

    @Bean
    public ActorRef orderProcessorActor(ActorSystem system, CreateOrderUseCase createOrderUseCase) {
        return system.actorOf(OrderProcessorActor.props(createOrderUseCase), "orderProcessor");
    }

    @Bean
    public Server grpcServer(ActorRef orderProcessorActor) throws IOException {
        Server server = ServerBuilder.forPort(grcpPort)
                .addService(new OrderServiceGrpc.OrderServiceImplBase() {
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

                        OrderProcessorActor.ProcessOrderMessage message = new OrderProcessorActor.ProcessOrderMessage(order, responseObserver);
                        orderProcessorActor.tell(message, ActorRef.noSender());
                    }
                })
                .build();

        server.start();
        return server;
    }

}