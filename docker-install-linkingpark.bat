rem docker pull postgres:latest
rem docker run --name linkingParkPostgresDB -p 5432:5432 -e POSTGRES_PASSWORD=inTheEnd -e POSTGRES_DB=linkingParkDB -d postgres
docker pull mongo:latest
docker run --name linkingParkMongoDB -p 27017:27017 -d mongo:latest

docker pull rabbitmq:latest
docker run -d --hostname linking-park-rabbit --name linking-park-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management
docker cp docker-rabbit.sh linking-park-rabbit:/root/docker-rabbit.sh
docker exec linking-park-rabbit bash /root/docker-rabbit.sh