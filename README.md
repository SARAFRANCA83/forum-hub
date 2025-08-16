# FórumHub

FórumHub é uma API REST desenvolvida em **Java 21** com **Spring Boot**, **Spring Security** e **JWT** para autenticação. O projeto permite o gerenciamento de usuários, cursos e tópicos de um fórum, com autenticação segura via token.

---

## Tecnologias

- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Hibernate / JPA
- MySQL
- Maven
- Lombok

---

## Estrutura do Projeto

- `controller/` → Classes responsáveis por receber as requisições HTTP.
- `model/` → Entidades do banco de dados (Usuário, Curso, Tópico).
- `repository/` → Interfaces JPA para persistência.
- `security/` → Configuração do Spring Security, filtro JWT e serviço de autenticação.
- `dto/` → Objetos de transferência de dados para requisições e respostas.
- `service/` → Lógica de negócio (opcional, dependendo da implementação).

---

## Funcionalidades

- Cadastro de usuários com validação de campos e criptografia de senha (BCrypt).
- Login de usuários com geração de token JWT.
- Autorização via token JWT para proteger endpoints.
- Cadastro e listagem de cursos.
- Cadastro e listagem de tópicos, vinculando curso e autor.
- Controle de acesso baseado em token JWT.

---

## **Configuração do Banco de Dados**

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
spring.datasource.username=root
spring.datasource.password=mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

forum.jwt.secret=SUA_CHAVE_SECRETA_AQUI
forum.jwt.expiration=86400000
```
## **Endpoints Principais**
Usuários

- **POST /usuarios** → Cadastrar usuário  
Exemplo de body:

```
{
  "nome": "Maria Joaquina",
  "email": "maria@email.com",
  "senha": "987654"
}
```

## **Autenticação**

- **POST /login** → Autenticar usuário e receber token JWT
Exemplo de body:

```
{
  "login": "maria@email.com",
  "senha": "987654"
}
```

Retorno:
```

{
  "token": "<token_jwt_aqui>"
}
```

Cursos

- **POST /cursos** → Criar curso (precisa de token JWT no header)
Header:

```
Authorization: Bearer <token_jwt>
```

Exemplo de body:

```
{
  "nome": "Java Básico",
  "categoria": "Programação"
}
```
- **GET /cursos** → Listar todos os cursos

Tópicos

- **POST /topicos** → Criar tópico (precisa de token JWT)
Exemplo de body:

```
{
  "titulo": "Dúvida sobre Java",
  "mensagem": "Como faço para usar List em Java?",
  "autorId": 1,
  "cursoId": 1
}
```
- **GET /topicos** → Listar todos os tópicos

---

Testando a API

Faça o cadastro de um usuário (POST /usuarios).

Faça login com o usuário cadastrado (POST /login) para obter o token JWT.

Para qualquer requisição que necessite autenticação, adicione o token no header:

```
Authorization: Bearer <token_jwt>
```
Teste endpoints de cursos e tópicos.

## **Observações**

Senhas são criptografadas usando BCrypt.

JWT é usado para autenticação stateless.

Certifique-se de criar usuários e cursos antes de criar tópicos, pois os tópicos dependem de autorId e cursoId.






