package pe.com.hacom.oms.infrastructure.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.hacom.oms.adapter.actor.OrderProcessorActor;
import pe.com.hacom.oms.adapter.grpc.OrderGrpcService;
import pe.com.hacom.oms.application.port.in.CreateOrderUseCase;

import java.io.IOException;

@Slf4j
@Configuration
public class GrpcServerConfig {

    @Value("${grpcPort}")
    private int grpcPort;

    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("order-system");
    }

    @Bean
    public ActorRef orderProcessorActor(ActorSystem system, CreateOrderUseCase createOrderUseCase) {
        return system.actorOf(OrderProcessorActor.props(createOrderUseCase), "orderProcessor");
    }

    @Bean
    public Server grpcServer(OrderGrpcService orderGrpcService) throws IOException {
        Server server = ServerBuilder.forPort(grpcPort)
                .addService(orderGrpcService)
                .build();

        server.start();
        log.info("Servidor gRPC iniciado en el puerto {}", grpcPort);
        return server;
    }

}