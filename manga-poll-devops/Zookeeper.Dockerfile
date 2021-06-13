FROM openjdk:11-jre-buster

WORKDIR /zookeeper

COPY kafka_2.13-2.8.0.tgz .

RUN tar -zxf kafka_2.13-2.8.0.tgz && \
mv kafka_2.13-2.8.0 kafka-runtime && \
rm kafka_2.13-2.8.0.tgz

CMD kafka-runtime/bin/zookeeper-server-start.sh kafka-runtime/config/zookeeper.properties
