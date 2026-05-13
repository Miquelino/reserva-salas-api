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
- [Environment](docs/postman/reserva-api.postman_environment.json)

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
