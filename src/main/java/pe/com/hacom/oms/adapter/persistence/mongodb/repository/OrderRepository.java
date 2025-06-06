package pe.com.hacom.oms.adapter.persistence.mongodb.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.com.hacom.oms.adapter.persistence.mongodb.document.OrderDocument;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<OrderDocument, ObjectId> {

    Mono<OrderDocument> findByOrderId(String orderId);
    Mono<Long> countByTsBetween(Instant from, Instant to);

}
