FROM openjdk:11-jre-buster

WORKDIR /kafka-broker

COPY kafka_2.13-2.8.0.tgz .

RUN tar -zxf kafka_2.13-2.8.0.tgz && \
mv kafka_2.13-2.8.0 kafka-runtime && \
rm kafka_2.13-2.8.0.tgz

COPY config/server.properties kafka-runtime/config/

ENV ZOOKEEPER_URL=zookeeper:2181

CMD echo zookeeper.connect=${ZOOKEEPER_URL} >> kafka-runtime/config/server.properties && \
kafka-runtime/bin/kafka-server-start.sh kafka-runtime/config/server.properties
