package pe.com.hacom.oms.infrastructure.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import pe.com.hacom.oms.infrastructure.codec.OffsetDateTimeCodec;

@Configuration
public class MongoConfig {

    @Value("${mongodbUri}")
    private String mongoUri;

    @Value("${mongodbDatabase}")
    private String mongoDatabase;

    @Bean
    public MongoClient reactiveMongoClient() {
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry customCodecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new OffsetDateTimeCodec()),
                defaultCodecRegistry
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUri))
                .codecRegistry(customCodecRegistry)
                .build();

        return MongoClients.create(settings);
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