# ReservAMF API - Gestão de Reservas Acadêmicas

## 📝 Sobre o Projeto
O **ReservAMF** é uma API RESTful completa desenvolvida para gerenciar a reserva de salas em ambiente acadêmico. O projeto foi inspirado na rotina da **AMF (Antonio Meneghetti Faculdade)**, permitindo que alunos e professores solicitem salas para reuniões, apresentações de TCC e eventos, com uma gestão centralizada para aprovações.

---

## ✨ Alguns pontos do projeto:
* **Validações de Regra de Negócio:** Algoritmos para impedir choque de horários, garantindo a integridade das agendas (uma reserva não pode sobrepor outra na mesma sala).
* **Segurança com JWT:** Implementação de autenticação stateless utilizando **Spring Security** e **JSON Web Tokens**.
* **Otimização de Consultas:** Resolução do problema **N+1** através de `JOIN FETCH` em JPQL, reduzindo drasticamente as chamadas ao banco de dados.
* **Cache de Alta Performance:** Integração com **Redis** para cache de dados, diminuindo a latência em consultas frequentes.
* **Imutabilidade com Records:** Uso de **Java Records** para a implementação de **DTOs (Data Transfer Objects)**.
* **Arquitetura em Camadas:** Organização rigorosa entre Controller, Service, Repository e Model.

---

## 🛠️ Stack do projeto
* **Back-End:** Java 21 & Spring Boot 3
* **Persistência/ORM:** JPA / Hibernate
* **Banco de Dados:** PostgreSQL
* **Cache:** Redis
* **Segurança:** Spring Security & Auth0 Java-JWT
* **Versionamento de Banco:** Flyway
* **Documentação:** Swagger / OpenAPI
* **Infraestrutura:** Docker
* **Build Tool:** Maven

---

## 🚀 Como Executar o Projeto

### 📋 Pré-requisitos
Você precisará ter instalado:
* [Git](https://git-scm.com/)
* [Docker](https://www.docker.com/)

### 🔧 Instruções de Instalação

**1. Clone o repositório:**
```bash
git clone [https://github.com/PedroLovatto99/reservAMF.git)
cd projeto-reservamf
```
**2. Configure o Ambiente:**
Crie um arquivo .env na raiz do projeto com as seguintes variáveis (ou ajuste o docker-compose.yml):
```bash
DB_NAME=reserva_db
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
TOKEN_PASSWORD=sua_chave_secreta_jwt
```
**3. Suba os containers:**
Este comando irá compilar a aplicação (via Maven no container), subir o banco PostgreSQL e o Redis:
```bash
docker-compose up --build
```
A API estará disponível em http://localhost:8080.
---
## 📖 Documentação da API (Swagger)
Visualize e teste todos os endpoints em tempo real acessando:

http://localhost:8080/swagger-ui/index.html
