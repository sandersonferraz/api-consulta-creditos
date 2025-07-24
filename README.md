# API de Consulta de Créditos

API RESTful em **Spring Boot (Java 21)** com front-end em **Angular 17**, que permite consultar créditos constituídos a partir do número da NFS-e ou número do crédito. Totalmente containerizada via Docker e com mensageria Kafka para auditoria.

---

## Como rodar o projeto localmente

### 1. Clone o repositório

```bash
git clone https://github.com/sandersonferraz/api-consulta-creditos.git
cd api-consulta-creditos
````

### 2. Suba os containers com Docker Compose

```bash
docker compose up -d --build # docker compose v2 
docker-compose up -d --build # docker coompose v1
```

Esse comando irá subir:

* PostgreSQL
* Kafka + Zookeeper
* API Spring Boot (`credito-api`)
* Frontend Angular (`credito-front`)

> As variáveis estão configuradas no `.env` na raiz do projeto.

---

## Como acessar

| Serviço     | URL                                                                            |
| ----------- | ------------------------------------------------------------------------------ |
| Frontend    | [http://localhost:4200](http://localhost:4200)                                 |
| Backend API | [http://localhost:8080/api/v1/creditos](http://localhost:8080/api/v1/creditos) |
| Swagger     | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |

---

## Endpoints

* `GET /api/v1/creditos/{numeroNfse}`
  Retorna todos os créditos relacionados a uma NFS-e.

* `GET /api/v1/creditos/credito/{numeroCredito}`
  Retorna os detalhes de um crédito específico.

---

## Estrutura dos Projetos

### Backend – Spring Boot (`credito-api`)

```
src/main/java/com/desafio/credito_api/
├── application/
│   └── service/
|   └── usecase/
├── domain/
│   ├── model/
│   ├── service/
│   └── usecase/
├── infrastructure/
│   ├── config/
│   ├── kafka/
│   └── repository/
├── web/
│   ├── controller/
│   ├── dto/
│   ├── exception/
│   └── mapper/
└── CreditoApiApplication.java
```

* `resources/` → application.yml, schema.sql, data.sql
* `test/` → testes unitários e de integração com JUnit e Mockito

### Frontend – Angular (`credito-front`)

```
src/app/
├── services/
├── models/
├── consts/
├── environments/
└── app.ts / app.routes.ts / app.config.ts
```

> **Observação**: testes no front ainda não foram implementados.

---

## O que foi implementado

### Arquitetura & Padrões

* Clean Architecture aplicada
* SOLID, DRY e KISS na prática
* MVC, Repository, Factory, Singleton
* Separação clara de camadas
* TDD com cobertura de testes unitários e de integração

### Mensageria

* Consulta por NFS-e ou número do crédito gera evento Kafka no tópico `consulta-creditos`
* Payload com detalhes para auditoria

---

## Base de dados

Scripts SQL para criação e carga de dados:

* `schema.sql` → Criação da tabela `credito` a `credito_audit_log`
* `data.sql` → População inicial com registros de exemplo

---

## Arquivo `.env`

Crie esse arquivo no diretorio raiz da aplicação

```env
DB_HOST=db
DB_NAME=credito
DB_USER=postgres
DB_PASS=postgres
DB_PORT=5432

KAFKA_BROKER_ID=1

SPRING_PROFILES_ACTIVE=dev
SPRING_JPA_HIBERNATE_DDL_AUTO=none
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
```

---

## Swagger

A documentação da API está disponível automaticamente em:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Autor

**Sanderson Ferraz**
[GitHub](https://github.com/sandersonferraz) | Full Stack Developer | Java + Angular
