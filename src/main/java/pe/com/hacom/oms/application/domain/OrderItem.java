package pe.com.hacom.oms.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class  OrderItem {

    private String itemId;
    private int quantity;

}