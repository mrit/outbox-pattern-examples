```shell script
make build
DEBEZIUM_VERSION=1.2 docker-compose up --build -d
```
```shell script
docker logs -f connect
```
```json
// http POST http://localhost:8083/connectors/outbox-connector/config
{
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "tasks.max": "1",
    "database.hostname": "db",
    "database.port": "3306",
    "database.user": "root",
    "database.password": "client",
    "database.dbname": "client",
    "database.server.name": "client",
    "transforms.outbox.table.whitelist" : "client.events",
    "transforms.outbox.table.field.event.id": "envent_id",
    "transforms.outbox.table.field.event.key": "event_key",
    "transforms.outbox.table.field.event.type": "event_type",
    "transforms.outbox.table.field.event.payload": "event_data",
    "transforms.outbox.table.field.event.payload.id": "event_key",
    "transforms.outbox.table.fields.additional.placement": "event_type:header:eventType",
    "value.converter": "io.debezium.converters.ByteBufferConverter",
    "transforms.outbox.route.by.field": "event_type",
    "transforms.outbox.route.topic.replacement": "client.events",
    "tombstones.on.delete": "false",
    "transforms": "outbox",
    "transforms.outbox.type": "io.debezium.transforms.outbox.EventRouter",    
    "database.history.kafka.bootstrap.servers": "broker:9092", 
    "database.history.kafka.topic": "db.log", 
    "include.schema.changes": "false"
    
}
```
```shell script
docker logs -f outbox-producer
docker logs -f kafka-consumer
```
```shell script
# for debug java outbox-producer and kafka-consumer from host, take IPs with cmd below
docker inspect -f '{{.Name}} - {{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps -aq)
```

links:

https://github.com/debezium/debezium-examples/tree/master/outbox
