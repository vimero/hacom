package pe.com.hacom.oms.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Order {

    private String orderId;
    private String customerId;
    private String phoneNumber;
    private List<OrderItem> items;

}
