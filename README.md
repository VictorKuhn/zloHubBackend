# ZloHub - Backend

## Sobre o Projeto
O **ZloHub - Backend** é o núcleo de processamento da plataforma ZloHub, uma aplicação destinada a conectar cuidadores e responsáveis por pessoas dependentes. Este backend gerencia todas as regras de negócio, integrações e operações, servindo como o pilar funcional da aplicação.

A plataforma ZloHub possibilita:
- O cadastro e gerenciamento de cuidadores.
- A criação de vagas por responsáveis.
- A candidatura de cuidadores às vagas disponíveis.
- A gestão de perfis de cuidadores e responsáveis.

Este backend foi desenvolvido com foco em escalabilidade, segurança e eficiência, utilizando tecnologias modernas e boas práticas de desenvolvimento.

---

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.3.5**
- **Hibernate (JPA)**
- **PostgreSQL**
- **Maven**
- **JUnit**
- **Jacoco** (Cobertura de Testes)
- **Elastic Beanstalk** (Deploy na AWS)
- **Relational Database Service** (Deploy na AWS)

---

## Funcionalidades
### Para Cuidadores
- Cadastro na plataforma.
- Visualização e candidatura às vagas disponíveis.
- Gerenciamento de perfil (edição de informações).
- Consulta de status das candidaturas.
- Recuperação e alteração de senha.

### Para Responsáveis
- Cadastro e gerenciamento de perfil.
- Criação de vagas de emprego.
- Visualização de cuidadores disponíveis.
- Gestão de candidaturas recebidas (aceitar ou recusar).
- Recuperação e alteração de senha.

---

## Arquitetura do Projeto
A aplicação segue o padrão **MVC (Model-View-Controller)**, com separação clara de responsabilidades:
- **Controller**: Gerencia requisições e respostas HTTP.
- **Service**: Contém a lógica de negócios.
- **Repository**: Gerencia interações com o banco de dados.
- **Model**: Define as entidades da aplicação.
- **DTO**: Facilita a comunicação entre frontend e backend.

---

## Como Rodar o Projeto Localmente

### Pré-requisitos
1. Java 17 ou superior.
2. Maven instalado.
3. Banco de dados PostgreSQL configurado.

### Passos para Execução
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/zlohub-back.git
   cd zlohub-back
   ```

2. Configure o banco de dados:
   - Crie um banco chamado `zlohub` no PostgreSQL.
   - Atualize o arquivo `application.properties` com as credenciais do banco.

3. Compile o projeto:
   ```bash
   mvn clean install
   ```

4. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

5. Teste o projeto:
   ```bash
   mvn clean test
   ```

---

## Endpoints da API
Segue uma lista dos principais endpoints, organizados por funcionalidade:

### **Vagas**
- **Criar Vaga**: `POST /api/vagas`
- **Listar Vagas**: `GET /api/vagas`
- **Buscar Vaga por ID**: `GET /api/vagas/id/{id}`
- **Atualizar Vaga**: `PUT /api/vagas/{id}`
- **Excluir Vaga**: `DELETE /api/vagas/{id}`

### **Candidaturas**
- **Criar Candidatura**: `POST /api/candidaturas`
- **Listar Candidaturas por Vaga**: `GET /api/candidaturas/vaga/{vagaId}`
- **Excluir Candidatura**: `DELETE /api/candidaturas/{id}`

### **Cuidadores**
- **Cadastrar Cuidador**: `POST /api/cuidadores`

---

## Testes e Cobertura
- **Frameworks utilizados**: Mockito e Spring Boot Test.
- **Cobertura de Testes**: ~97%, garantida pelo Jacoco.
- **Exemplo de Teste**:
```java
@Test
void shouldCreateVagaSuccessfully() {
    VagaDTO vagaDTO = new VagaDTO("12345678901", "Cuidador", "Descrição da vaga");
    ResponseEntity<?> response = vagaController.createVaga(vagaDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
}
```

---

## Deploy na AWS
O projeto foi implantado utilizando o **AWS Elastic Beanstalk**, com integração ao **RDS PostgreSQL** para o banco de dados.

---

## Contribuições
Sinta-se à vontade para abrir issues ou pull requests. Sugestões e melhorias são bem-vindas.
