docker pull postgres:latest
docker run --name linkingParkPostgresDB -p 5432:5432 -e POSTGRES_PASSWORD=inTheEnd -e POSTGRES_DB=linkingParkDB -d postgres

docker pull rabbitmq:latest
docker run -d --hostname linking-park-rabbit --name linking-park-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management
docker cp docker-rabbit.sh linking-park-rabbit:/root/docker-rabbit.sh
docker exec linking-park-rabbit bash /root/docker-rabbit.sh