docker build -t manga-poll:zookeeper -f Zookeeper.Dockerfile .
docker build -t manga-poll:broker -f Broker.Dockerfile .
docker build -t manga-poll:connect -f Connect.Dockerfile .