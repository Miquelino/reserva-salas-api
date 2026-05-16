# Sistema de Reserva de Salas

Projeto criado para a Checkpoint Desenvolvimento Back-End Java - Nível 1 da Alura.

API REST desenvolvida com Java + Spring Boot para gerenciamento de usuários, salas e reservas de ambientes.

---
# Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Docker
- Docker Compose
- Lombok
- Jakarta Validation
- Springdoc OpenAPI / Swagger

---

# Funcionalidades

## Usuários
- Cadastrar usuário
- Listar usuários
- Atualizar usuário
- Deletar usuário

## Salas
- Cadastrar sala
- Listar salas
- Atualizar sala
- Deletar sala

## Reservas
- Criar reserva
- Listar reservas
- Atualizar reserva
- Deletar reserva
- Validação de conflito de horário

---

## 📬 Collection do Postman

Para facilitar os testes da API, a collection e o environment do Postman estão disponíveis abaixo:

### 📁 Arquivos

- [Collection da API](docs/postman/reserva-api.postman_collection.json)

### 🚀 Como importar no Postman

1. Abra o Postman
2. Clique em **Import**
3. Arraste os arquivos `.json`
4. Selecione o environment importado
5. Teste os endpoints
# Estrutura do Projeto

```text
src
 ├── controllers
 ├── dto
 ├── entities
 ├── repository
 ├── service
 └── resources
```

## 🐳 Como Rodar o Projeto com Docker

### 1. Clone o repositório

```bash
git clone https://github.com/Miquelino/reserva-salas-api.git
```

---

### 2. Entre na pasta do projeto

```bash
cd reserva-salas-api
```

---

### 3. Gere o `.jar` da aplicação

```bash
mvn clean package
```

---

### 4. Gere a imagem Docker

```bash
docker build -t reserva-salas-api .
```

---

### 5. Execute o container

```bash
docker run -p 8080:8080 reserva-salas-api
```

A API estará disponível em:

```text
http://localhost:8080
```

## Swagger / OpenAPI

Com a aplicacao rodando, a documentacao interativa fica disponivel em:

```text
http://localhost:8080/swagger-ui.html
```

O contrato OpenAPI em JSON fica disponivel em:

```text
http://localhost:8080/v3/api-docs
```

---

## 📬 Como Importar a Collection do Postman

### 1. Abra o Postman

### 2. Clique em `Import`

### 3. Selecione os arquivos localizados em:

```text
docs/postman/
```

### 4. Importe:

- `reserva-api.postman_collection.json`
- `reserva-api.postman_environment.json`

---

## 📌 Regras de Negócio

### Usuários

- Nome obrigatório
- Email obrigatório e único
- CPF único
- Idade deve ser maior que zero

### Salas

- Nome obrigatório
- Capacidade deve ser maior que zero
- Apenas salas ativas podem receber reservas

### Reservas

- Data de início deve ser antes da data de fim
- Não é permitido conflito de horário na mesma sala
- Reservas canceladas não entram na validação de conflito
- Toda reserva deve possuir:
  - uma sala
  - um usuário

---

## 🧩 Diagrama Simples das Entidades

```text
Usuario
  └── 1:N → Reserva

Sala
  └── 1:N → Reserva

Reserva
  ├── N:1 → Usuario
  └── N:1 → Sala
```

---

## 📷 Prints dos Endpoints

Os prints dos endpoints serão adicionados futuramente.

## 🚀 Próximas Atualizações

Melhorias planejadas para evolução da API:

- Padronizar rotas seguindo boas práticas REST
- Criar tratamento global de erros com `@RestControllerAdvice`
- Implementar cancelamento de reserva por status, sem deletar do banco
- Validar conflito de horário também na atualização de reservas
- Separar DTOs por responsabilidade:
  - criação
  - atualização
  - detalhamento
- Implementar migrations com Flyway
- Criar testes automatizados para regras de negócio
- Melhorar mensagens de erro
- Adicionar autenticação e autorização futuramente

---

## 📌 Melhorias na Documentação

Também serão adicionados ao README:

- Como rodar o projeto com Docker
- Como importar a collection do Postman
- Prints dos endpoints no Postman
- Regras de negócio da aplicação
- Diagrama simples das entidades e relacionamentos
