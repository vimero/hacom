package pe.com.hacom.oms.adapter.actor;

import io.grpc.stub.StreamObserver;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.grpc.OrderResponse;

public record ProcessOrderMessage(Order order, StreamObserver<OrderResponse> responseObserver) {

}
