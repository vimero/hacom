package pe.com.hacom.oms.adapter.actor;

import lombok.RequiredArgsConstructor;
import pe.com.hacom.oms.application.domain.Order;

public record OrderCreatedMessage(Order order) {

}