package pe.com.hacom.oms;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pe.com.hacom.oms.grpc.OrderItem;
import pe.com.hacom.oms.grpc.OrderRequest;
import pe.com.hacom.oms.grpc.OrderResponse;
import pe.com.hacom.oms.grpc.OrderServiceGrpc;

public class GrcpOrderClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        OrderServiceGrpc.OrderServiceBlockingStub stub = OrderServiceGrpc.newBlockingStub(channel);

        OrderRequest request = OrderRequest.newBuilder()
                .setOrderId("ORD-1001")
                .setCustomerId("CUST-001")
                .setPhoneNumber("+51987654321")
                .addItems(OrderItem.newBuilder().setItemId("ITEM-1").setQuantity(2).build())
                .addItems(OrderItem.newBuilder().setItemId("ITEM-2").setQuantity(1).build())
                .build();

        OrderResponse response = stub.insertOrder(request);
        System.out.println("Response: Order ID = " + response.getOrderId() + ", Status = " + response.getStatus());

        channel.shutdown();
    }

}