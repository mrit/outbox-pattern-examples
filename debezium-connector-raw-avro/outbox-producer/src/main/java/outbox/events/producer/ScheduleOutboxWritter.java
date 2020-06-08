package outbox.events.producer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

import events.ClientEvent;
import events.Plan;
import org.apache.avro.message.BinaryMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleOutboxWritter {

  public static Logger logger = LoggerFactory.getLogger(ScheduleOutboxWritter.class);

  private final OutboxEventRepository repository;
  private final BinaryMessageEncoder<ClientEvent> encoder = ClientEvent.getEncoder();

  public ScheduleOutboxWritter(final OutboxEventRepository repository) {
    this.repository = repository;
  }

  @Scheduled(fixedDelay = 60000)
  public void write() throws IOException {
    final Plan.Builder planBuilder = Plan.newBuilder();
    final ClientEvent.Builder clientBuilder = ClientEvent.newBuilder();
    final ClientEvent event =
        clientBuilder
            .setId(UUID.randomUUID().toString())
            .setPlans(
                Arrays.asList(
                    planBuilder.setPlanId(1).build(),
                    planBuilder.setPlanId(2).build(),
                    planBuilder.setPlanId(3).build()))
            .build();
    final ByteBuffer buffer = encoder.encode(event);
    
    repository.save(OutboxEvent.create(buffer.array()));
    logger.trace("event={}", event);
  }
}
