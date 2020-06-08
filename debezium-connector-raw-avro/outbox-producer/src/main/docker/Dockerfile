#FROM openjdk:8
FROM java:8

ADD ./build/distributions/*-boot*.zip /outbox-producer.zip

RUN unzip /outbox-producer.zip && \
    rm -rf *.zip && \
    mv outbox-producer* /outbox-producer

EXPOSE 80 443

ENTRYPOINT [ "/outbox-producer/bin/outbox-producer" ]
