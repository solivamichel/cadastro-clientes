# Cliente API

API REST para cadastro de clientes e seus logradouros, com upload de logotipo.

## Tecnologias
- Java 8
- Spring Boot 2.7
- Spring Data JPA
- SQL Server 2016+
- Lombok
- Spring Security (Basic Auth)

## Funcionalidades
- Criar, listar, atualizar e deletar clientes
- Cadastro de múltiplos logradouros por cliente
- Upload e download de logotipo (imagem)
- Proteção de acesso por autenticação básica

## Banco de dados
Configuraçao `application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=cliente_db
spring.datasource.username=sa
spring.datasource.password=Su@Senha123
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

## Como executar
mvn clean install
mvn spring-boot:run

http://localhost:8080/clientes

## Usuário e senha de acesso (Basic Auth)
Usuário: admin
Senha: admin123

## Endpoints principais

POST /clientes
GET /clientes
GET /clientes/{id}
PUT /clientes/{id}
DELETE /clientes/{id}
POST /clientes/{id}/logotipo
GET /clientes/{id}/logotipo
