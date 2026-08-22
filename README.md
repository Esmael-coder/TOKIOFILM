# TOKIOFILM

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-Spring%20Boot-green)
![MySQL](https://img.shields.io/badge/database-MySQL-blue)
![Docker](https://img.shields.io/badge/container-Docker-blue)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

Plataforma web de cinema onde os utilizadores podem criar uma conta, cadastrar
filmes e profissionais do cinema, atribuir classificações e publicar reviews.

> **Estado do projeto:** Em desenvolvimento.

## Funcionalidades

- [x] Registo e autenticação de utilizadores
- [x] Autenticação da API com JWT
- [x] Cadastro de filmes
- [x] Cadastro de atores, diretores e outros profissionais
- [x] Pesquisa de filmes
- [x] Classificação de filmes de 0 a 10
- [x] Publicação de reviews
- [x] Privilégios de Admin
- [x] Migração diária de filmes para CSV com Spring Batch
- [x] Documentação da API com Swagger
- [x] Execução da aplicação com Docker
- [ ] Testes automatizados
- [ ] Melhorias na interface com react
- [ ] Deploy da aplicação

##  Tecnologias utilizadas

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Batch
- Thymeleaf
- Bootstrap
- MySQL
- JWT
- Swagger / OpenAPI
- Docker
- Nginx
- Maven

## Como executar o projeto

### Pré-requisitos

Para executar a aplicação é necessário ter instalado:

- Git
- Docker Desktop
- Docker Compose

### Clonar o repositório

```bash
git clone https://github.com/Esmael-coder/TOKIOFILM.git
cd TOKIOFILM
```

### Configurar as variáveis de ambiente

Cria um ficheiro chamado `.env` na raiz do projeto:

```properties
SQL_DATABASE_URL=jdbc:mysql://mysql:3306/mydb
SQL_DATABASE_NAME=mydb
SQL_DATABASE_USERNAME=nome do utilizador
SQL_DATABASE_PASSWORD=password
JWT_EXPIRATION=coloca o tempo que preferir em milliseconds
JWT_SECRET=SUA_CHAVE_SECRETA
```

### Executar com Docker

```bash
docker compose up --build
```

Depois, abre no navegador:

```text
http://localhost
```

## Documentação da API

Com a aplicação em execução, o Swagger pode ser consultado em:

```text
http://localhost/swagger-ui/index.html
```

## Segurança

A aplicação utiliza dois mecanismos de autenticação:

- Sessão e formulário de login para a plataforma web
- JWT para os endpoints da API REST

As passwords dos utilizadores são armazenadas com encriptação BCrypt.

## Estrutura principal

```text
TOKIOFILM/
├── docker/
├── nginx/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Autor

Desenvolvido por **Esmael Monteiro**.

- GitHub: [@Esmael-coder](https://github.com/Esmael-coder)

## Licença

Este projeto foi desenvolvido para fins académicos e de aprendizagem.
