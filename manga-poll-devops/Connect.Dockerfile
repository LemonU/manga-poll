FROM openjdk:11-jre-buster

WORKDIR /kafka-connect

COPY kafka_2.13-2.8.0.tgz .

RUN tar -zxf kafka_2.13-2.8.0.tgz && \
mv kafka_2.13-2.8.0 kafka-runtime && \
rm kafka_2.13-2.8.0.tgz

COPY config/connect-distributed.properties kafka-runtime/config/

RUN cd kafka-runtime/libs && \
wget 'https://github.com/LemonU/manga-poll/releases/download/0.1/manga-poll-connectors-0.1.jar'

ENV BROKER_URL=broker:9092

CMD echo bootstrap.servers=${BROKER_URL} >> kafka-runtime/config/connect-distributed.properties && \
kafka-runtime/bin/connect-distributed.sh kafka-runtime/config/connect-distributed.properties