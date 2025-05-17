package pe.com.hacom.oms.infrastructure.codec;

import org.bson.*;
import org.bson.codecs.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeCodec implements Codec<OffsetDateTime> {

    @Override
    public OffsetDateTime decode(BsonReader reader, DecoderContext decoderContext) {
        return OffsetDateTime.parse(reader.readString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    @Override
    public void encode(BsonWriter writer, OffsetDateTime value, EncoderContext encoderContext) {
        writer.writeString(value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @Override
    public Class<OffsetDateTime> getEncoderClass() {
        return OffsetDateTime.class;
    }
}

