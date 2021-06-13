docker build -t lemonu/manga-poll:zookeeper -f Zookeeper.Dockerfile .
docker build -t lemonu/manga-poll:broker -f Broker.Dockerfile .
docker build -t lemonu/manga-poll:connect -f Connect.Dockerfile .