package kafka.consumer;

import java.util.Arrays;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;

import org.apache.avro.Schema;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//https://medium.com/@wujiaojiao82/complete-confluence-compliant-avro-deserializer-that-can-do-schema-evolution-3292656db1c2
public class AvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AvroDeserializer.class);

  protected final Class<T> targetType;

  public AvroDeserializer(final Class<T> targetType) {
    this.targetType = targetType;
  }

  @Override
  public void close() {
    // No-op
  }

  @Override
  public void configure(final Map<String, ?> arg0, final boolean arg1) {
    // No-op
  }

  @SuppressWarnings("unchecked")
  @Override
  public T deserialize(final String topic, final byte[] data) {
    try {
      T result = null;

      if (data != null) {
        LOGGER.trace("data='{}'", DatatypeConverter.printHexBinary(data));

        final T instance = targetType.newInstance();
        final Schema schema = instance.getSchema();

        final BinaryMessageDecoder<T> decoder =
            new BinaryMessageDecoder<>(new SpecificData(), schema);

        result = (T) decoder.decode(data);
        LOGGER.trace("deserialized data='{}'", result);
      }
      return result;
    } catch (final Exception ex) {
      throw new SerializationException(
          "Can't deserialize data '" + Arrays.toString(data) + "' from topic '" + topic + "'", ex);
    }
  }
}
