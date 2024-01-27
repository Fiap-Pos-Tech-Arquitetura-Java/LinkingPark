# LinkingPark
Novo sistema de parquimetro

Para rodar a aplicação primeiro deve ser criado um banco de dados postgres
Sugerimos o uso do Docker comandos.
docker pull postgres:latest
docker run --name linkingParkPostgresDB -p 5432:5432 -e POSTGRES_PASSWORD=inTheEnd -e POSTGRES_DB=linkingParkDB -d postgres