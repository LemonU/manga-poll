FROM openjdk:11-jre-buster

WORKDIR /zookeeper

COPY kafka_2.13-2.8.0.tgz .

RUN tar -zxf kafka_2.13-2.8.0.tgz && \
mv kafka_2.13-2.8.0 kafka-runtime && \
rm kafka_2.13-2.8.0.tgz

ENV ZOOKEEPER_PORT=2181

COPY ./config/zookeeper.properties kafka-runtime/config

CMD echo clientPort=${ZOOKEEPER_PORT} >> kafka-runtime/config/zookeeper.properties && \
kafka-runtime/bin/zookeeper-server-start.sh kafka-runtime/config/zookeeper.properties
