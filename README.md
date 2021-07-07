# [GraphQL-WEBFLUX-SWAGGER] Kotlin Spring Example

![](https://img.shields.io/github/last-commit/gongdongho12/spring-dongholab-graphql) ![](https://img.shields.io/badge/license-MIT-green)

> Created as a sample to be used as a spring boot kotlin project.

##The modules used are as follows.
* JPA (spring-boot-starter-data-jpa)
* R2DBC (spring-boot-starter-data-r2dbc)
* MYSQL (mysql-connector-java, r2dbc-mariadb)
* SWAGGER (springfox-boot-starter)
* WEB-FLUX (spring-boot-starter-webflux)
* JWT (jjwt)
* GraphQL (graphql-kotlin-spring-server)

## Application Endpoint
* swagger: <http://localhost:8080/swagger-ui/index.html>
* graphql-playground: <http://localhost:8080/playground>
* graphql-index: <http://localhost:8080/graphql>

## MYSQL Configuration
> src > resource > application.yml

* Edit: spring.datasource