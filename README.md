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

# Estrutura do Projeto

```text
src
 ├── controllers
 ├── dto
 ├── entities
 ├── repository
 ├── service
 └── resources