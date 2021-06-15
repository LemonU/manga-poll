FROM openjdk:11-jre-buster

WORKDIR /kafka-connect

COPY kafka_2.13-2.8.0.tgz .

RUN tar -zxf kafka_2.13-2.8.0.tgz && \
mv kafka_2.13-2.8.0 kafka-runtime && \
rm kafka_2.13-2.8.0.tgz

COPY config/connect-distributed.properties kafka-runtime/config/

RUN cd kafka-runtime/libs && \
wget 'https://github.com/LemonU/manga-poll/releases/download/0.1/manga-poll-connectors-0.1.jar'

ENV BROKER_URL=broker:29092
ENV OFFSET_FLUSH_INTERVAL_MS=10000

CMD cd kafka-runtime && \
echo bootstrap.servers=${BROKER_URL} >> config/connect-distributed.properties && \
echo offset.flush.interval.ms=${OFFSET_FLUSH_INTERVAL_MS} >> config/connect-distributed.properties && \
cat config/connect-distributed.properties && \
bin/connect-distributed.sh config/connect-distributed.properties