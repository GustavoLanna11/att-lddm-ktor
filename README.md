# Projeto LDDM - API Times de Futebol
**Aluno:** [Gustavo Marques de Lanna - DSM5]

Este projeto é uma API REST para gerenciamento de times e jogadores, desenvolvida como parte da disciplina de Laboratório de Desenvolvimento para Dispositivos Móveis. A aplicação utiliza Kotlin, Ktor, Exposed ORM e um banco de dados PostgreSQL rodando via Docker.

---

## Como Rodar o Projeto em Casa

**Pré-requisitos**
* IntelliJ IDEA (Community ou Ultimate).
* Docker Desktop (Certifique-se de que ele esteja aberto e rodando).
* JDK 17 ou superior configurado no IntelliJ.

---

**Subir o Banco de Dados (Docker)**
O projeto já conta com um arquivo `docker-compose.yml`. Para subir o banco:
- Abra o terminal na pasta raiz do projeto.
- Digite o comando:
   ```bash
   docker-compose up -d
O banco será criado automaticamente na porta 5433 com as credenciais padrão.

---

**Executando a API**
- No IntelliJ, aguarde a sincronização do Gradle.
- Vá até src/main/kotlin/com/fatec/att_lddm_ktor/Application.kt.
- Clique no botão de Run (Play verde).
- Confirme no console a mensagem: ✅ BANCO DE DADOS CONECTADO COM SUCESSO!.

---

**Documentação e Testes (Swagger)**
A API utiliza Swagger UI para facilitar os testes das rotas sem a necessidade de ferramentas externas como Postman ou Insomnia.

URL de Acesso: ```http://localhost:8080/swagger```

---

**Como testar:**
- Clique em uma rota (ex: POST /times).
- Clique no botão "Try it out".
- Preencha os parâmetros (nome, estádio, etc).
- Clique em "Execute" e veja o resultado no banco.

---

**Rotas da API (Endpoints)**
Times (/times)
- ```GET /times```: Lista todos os times cadastrados no banco.

- ```POST /times```: Cadastra um novo time (Parâmetros: nome, estadio, cidade).

- ```PUT /times/{id}```: Atualiza os dados de um time existente.

- ```DELETE /times/{id}```: Remove um time permanentemente.

---

Jogadores (/jogadores)
- ```GET /jogadores```: Lista todos os jogadores e seus respectivos times.

- ```POST /jogadores```: Cadastra um jogador (Parâmetros: nome, posicao, teamId).

- ```PUT /jogadores/{id}```: Atualiza nome ou posição de um jogador.

- ```DELETE /jogadores/{id}```: Remove um jogador do sistema.

---

**Tecnologias Utilizadas**
- ```Kotlin & Ktor```: Framework assíncrono para servidores.

- ```Exposed ORM```: Biblioteca Kotlin SQL para manipulação do banco de dados.

- ```PostgreSQL```: Banco de dados relacional para persistência de dados.

- ```Docker```: Containerização do banco para evitar conflitos de instalação.

- ```Swagger/OpenAPI```: Documentação interativa integrada.

---

**Estrutura do Código**
- ```database/```: Contém o DatabaseFactory (conexão) e Tables.kt (esquema do banco).

- ```repository/```: Onde fica o FootballRepository.kt com a lógica de CRUD.

- ```routes/```: Divisão de rotas em arquivos separados (TeamRoutes.kt e PlayerRoutes.kt).

- ```resources/```: Contém o documentation.yaml que alimenta o Swagger.
