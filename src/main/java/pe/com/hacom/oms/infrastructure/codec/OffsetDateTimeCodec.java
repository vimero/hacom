package pe.com.hacom.oms.infrastructure.codec;

import lombok.extern.slf4j.Slf4j;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Slf4j
public class OffsetDateTimeCodec implements Codec<OffsetDateTime> {

    @Override
    public void encode(BsonWriter writer, OffsetDateTime value, EncoderContext encoderContext) {
        writer.writeDateTime(value.toInstant().toEpochMilli());
    }

    @Override
    public OffsetDateTime decode(BsonReader reader, DecoderContext decoderContext) {
        long millis = reader.readDateTime();
        return Instant.ofEpochMilli(millis).atOffset(ZoneOffset.UTC);
    }

    @Override
    public Class<OffsetDateTime> getEncoderClass() {
        return OffsetDateTime.class;
    }

}

