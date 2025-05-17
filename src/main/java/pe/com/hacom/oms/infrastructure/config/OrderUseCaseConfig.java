package pe.com.hacom.oms.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.hacom.oms.application.port.in.CreateOrderUseCase;
import pe.com.hacom.oms.application.port.out.OrderPersistence;
import pe.com.hacom.oms.application.usecase.CreateOrderService;
import pe.com.hacom.oms.application.usecase.GetOrderService;

@Configuration
public class OrderUseCaseConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderPersistence orderPersistence){
        return new CreateOrderService(orderPersistence);
    }

    @Bean
    public GetOrderService getOrderService(OrderPersistence orderPersistence){
        return new GetOrderService(orderPersistence);
    }

}
