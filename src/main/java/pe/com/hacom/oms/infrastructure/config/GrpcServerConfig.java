package pe.com.hacom.oms.infrastructure.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.application.domain.OrderItem;
import pe.com.hacom.oms.application.port.in.CreateOrderUseCase;
import pe.com.hacom.oms.grpc.OrderRequest;
import pe.com.hacom.oms.grpc.OrderResponse;
import pe.com.hacom.oms.grpc.OrderServiceGrpc;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    @Value("${grcpPort}")
    private int grcpPort;

    @Bean
    public Server grpcServer(CreateOrderUseCase createOrderUseCase) throws IOException {
        Server server = ServerBuilder.forPort(grcpPort)
                .addService(new OrderServiceGrpc.OrderServiceImplBase() {

                    @Override
                    public void insertOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
                        var items = request.getItemsList().stream()
                                .map(i -> new OrderItem(i.getItemId(), i.getQuantity()))
                                .toList();

                        var order = new Order(
                                request.getOrderId(),
                                request.getCustomerId(),
                                request.getPhoneNumber(),
                                items
                        );

                        createOrderUseCase.createOrder(order);

                        OrderResponse response = OrderResponse.newBuilder()
                                .setOrderId(order.getOrderId())
                                .setStatus("RECEIVED")
                                .build();

                        responseObserver.onNext(response);
                        responseObserver.onCompleted();
                    }
                })
                .build();

        server.start();
        return server;
    }

}