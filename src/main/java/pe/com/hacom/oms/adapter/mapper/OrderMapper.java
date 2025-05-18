package pe.com.hacom.oms.adapter.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pe.com.hacom.oms.adapter.persistence.mongodb.document.OrderDocument;
import pe.com.hacom.oms.application.domain.Order;
import pe.com.hacom.oms.application.domain.OrderItem;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customerPhoneNumber", target = "phoneNumber")
    @Mapping(source = "items", target = "items", qualifiedByName = "mapItemsToOrderItems")
    Order toOrder(OrderDocument document);

    @InheritInverseConfiguration
    @Mapping(target = "_id", ignore = true)
    @Mapping(target = "ts", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "status", constant = "RECEIVED")
    @Mapping(source = "items", target = "items", qualifiedByName = "mapOrderItemsToItems")
    OrderDocument toDocument(Order order);

    @Named("mapItemsToOrderItems")
    default List<OrderItem> mapItemsToOrderItems(List<String> items) {
        if (items == null) {
            return Collections.emptyList();
        }
        return items.stream()
                .map(item -> new OrderItem(item, 1))
                .toList();
    }

    @Named("mapOrderItemsToItems")
    default List<String> mapOrderItemsToItems(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return Collections.emptyList();
        }
        return orderItems.stream()
                .map(OrderItem::getItemId)
                .toList();
    }
}
