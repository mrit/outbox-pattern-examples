package outbox.events.producer;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class OutboxEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "envent_id")
  private Long id;

  @Column(name = "event_key")
  private String eventKey;

  @Enumerated(EnumType.STRING)
  @Column(name = "event_type")
  private EventType eventType;

  @Column(name = "event_data", columnDefinition = "BLOB")
  private byte[] data;

  @Column(name = "ts", columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime ts;

  public static OutboxEvent create(final byte[] data) {
    final OutboxEvent outboxEvent = new OutboxEvent();
    outboxEvent.setEventKey(UUID.randomUUID().toString());
    outboxEvent.setEventType(EventType.ClientEvent);
    outboxEvent.setData(data);
    return outboxEvent;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getEventKey() {
    return eventKey;
  }

  public void setEventKey(final String eventKey) {
    this.eventKey = eventKey;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(final EventType eventType) {
    this.eventType = eventType;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(final byte[] data) {
    this.data = data;
  }
}
