package pe.com.hacom.oms.adapter.actor;

import pe.com.hacom.oms.application.domain.Order;

public record OrderCreatedMessage(Order order) {

}