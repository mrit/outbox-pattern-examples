package kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    public static Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @KafkaListener(topics = "client.events")
    public void listen(final ConsumerRecord<?, ?> cr) {
        logger.info(
                "offset={} key={} value={} headers={}", cr.offset(), cr.key(), cr.value(), cr.headers());
    }
}
