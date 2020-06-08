package kafka.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConfig {

  @Bean
  public Map<String, Object> consumerConfigs(
      @Value("${spring.kafka.bootstrap-servers}") final String bootstrapServers) {

    final Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "foo");
    return props;
  }

  @Bean
  public ConsumerFactory<String, events.ClientEvent> consumerFactory() {

    return new DefaultKafkaConsumerFactory<>(
        consumerConfigs(null),
        new StringDeserializer(),
        new AvroDeserializer<>(events.ClientEvent.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, events.ClientEvent>
      kafkaListenerContainerFactory() {

    final ConcurrentKafkaListenerContainerFactory<String, events.ClientEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}
