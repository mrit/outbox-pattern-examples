#FROM openjdk:8
FROM java:8

ADD ./build/distributions/*-boot*.zip /kafka-consumer.zip

RUN unzip /kafka-consumer.zip && \
    rm -rf *.zip && \
    mv kafka-consumer* /kafka-consumer

EXPOSE 80 443

ENTRYPOINT [ "/kafka-consumer/bin/kafka-consumer" ]
