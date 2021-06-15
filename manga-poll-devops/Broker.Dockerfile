FROM openjdk:11-jre-buster

WORKDIR /kafka-broker

COPY kafka_2.13-2.8.0.tgz .

RUN tar -zxf kafka_2.13-2.8.0.tgz && \
mv kafka_2.13-2.8.0 kafka-runtime && \
rm kafka_2.13-2.8.0.tgz

COPY config/server.properties kafka-runtime/config/

ENV ZOOKEEPER_URL=zookeeper:2181
ENV LISTENERS=INTERNAL://broker:29092,HOST://broker:9092
ENV ADVERTISED_LISTENERS=INTERNAL://broker:29092,HOST://localhost:9092
ENV LISTENER_SECURITY_PROTOCOL_MAP=INTERNAL:PLAINTEXT,HOST:PLAINTEXT,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL
ENV INTER_BROKER_LISTENER_NAME=INTERNAL

CMD cd kafka-runtime && \
echo zookeeper.connect=${ZOOKEEPER_URL} >> config/server.properties && \
echo listeners=${LISTENERS} >> config/server.properties && \
echo advertised.listeners=${ADVERTISED_LISTENERS} >> config/server.properties && \
echo listener.security.protocol.map=${LISTENER_SECURITY_PROTOCOL_MAP} >> config/server.properties && \
echo inter.broker.listener.name=${INTER_BROKER_LISTENER_NAME} >> config/server.properties && \
cat config/server.properties && \
bin/kafka-server-start.sh config/server.properties
