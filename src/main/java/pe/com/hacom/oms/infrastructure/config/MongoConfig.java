package pe.com.hacom.oms.infrastructure.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

@Configuration
public class MongoConfig {

    @Value("${mongodbUri}")
    private String mongoUri;

    @Value("${mongodbDatabase}")
    private String mongoDatabase;

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUri))
                .build());
    }

    @Bean
    public ReactiveMongoDatabaseFactory reactiveMongoDbFactory(MongoClient client) {
        return new SimpleReactiveMongoDatabaseFactory(client, mongoDatabase);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(ReactiveMongoDatabaseFactory factory) {
        return new ReactiveMongoTemplate(factory);
    }
}