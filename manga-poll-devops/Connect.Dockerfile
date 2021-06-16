FROM openjdk:11-jre-buster

WORKDIR /kafka-connect

COPY kafka_2.13-2.8.0.tgz .

RUN tar -zxf kafka_2.13-2.8.0.tgz && \
mv kafka_2.13-2.8.0 kafka-runtime && \
rm kafka_2.13-2.8.0.tgz

COPY config/connect-distributed.properties kafka-runtime/config/

ENV BROKER_URL=broker:29092
ENV OFFSET_FLUSH_INTERVAL_MS=10000
ENV PLUGIN_PATH=/opt/connectors

RUN cd kafka-runtime && \
echo bootstrap.servers=${BROKER_URL} >> config/connect-distributed.properties && \
echo offset.flush.interval.ms=${OFFSET_FLUSH_INTERVAL_MS} >> config/connect-distributed.properties && \
echo plugin.path=${PLUGIN_PATH} >> config/connect-distributed.properties && \
cat config/connect-distributed.properties

ENTRYPOINT ["kafka-runtime/bin/connect-distributed.sh", "kafka-runtime/config/connect-distributed.properties"]