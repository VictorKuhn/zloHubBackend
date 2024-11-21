# **ZloHub - Backend**

Este é o repositório do backend da aplicação **ZloHub**, um sistema criado para conectar cuidadores e responsáveis por pessoas dependentes. Ele fornece funcionalidades para gerenciar **vagas de trabalho**, **candidaturas** e **usuários (cuidadores)**. O projeto foi desenvolvido utilizando **Spring Boot** com integração ao banco de dados **PostgreSQL** e implementação de boas práticas como **DTOs**, **testes unitários** e **CI/CD**.

---

## **Tecnologias Utilizadas**

- **Java 17**
- **Spring Boot 3.3.5**
- **Hibernate (JPA)**
- **PostgreSQL**
- **Maven**
- **Jacoco** (Cobertura de Testes)
- **Elastic Beanstalk** (Deploy na AWS)

---

## **Como Rodar o Projeto Localmente**

### Pré-requisitos

1. **Java 17** ou superior.
2. **Maven** instalado.
3. Banco de dados **PostgreSQL** configurado.
4. Configurar variáveis de ambiente ou alterar o arquivo `application.properties` com as credenciais do banco.

### Rodar o Projeto

# Clonar o repositório
```
git clone https://github.com/seu-usuario/zlohub-back.git
cd zlohub-back
```

# Compilar o projeto
```
mvn clean install
```

# Executar
```bash
mvn spring-boot:run
Rodar Testes
bash
Copiar código
# Testar o projeto e gerar cobertura de testes
mvn clean test
```

# Endpoints da Aplicação
Aqui estão listados os principais endpoints da aplicação, suas funcionalidades e exemplos de requisição.

### **Vagas**

1. Criar Vaga
```
POST /api/vagas
```
Descrição: Endpoint para criar uma nova vaga.

Exemplo de Payload:

```json
{
  "cpfResponsavel": "12345678901",
  "titulo": "Cuidador para criança especial",
  "descricao": "Acompanhamento no período escolar.",
  "dataHoraInicio": "2024-12-01T08:00:00",
  "dataHoraFim": "2024-12-01T12:00:00",
  "cidade": "São Paulo",
  "estado": "SP",
  "tipoDependente": "CRIANCA",
  "idadeDependente": 7,
  "doencaDiagnosticada": "Autismo",
  "status": "ATIVA"
}
```

Resposta:

```json
{
  "id": 1,
  "cpfResponsavel": "12345678901",
  "titulo": "Cuidador para criança especial",
  "descricao": "Acompanhamento no período escolar.",
  "cidade": "São Paulo",
  "estado": "SP",
  "tipoDependente": "CRIANCA",
  "idadeDependente": 7,
  "doencaDiagnosticada": "Autismo",
  "status": "ATIVA"
}
```

2. Listar Todas as Vagas
```
GET /api/vagas
```

Descrição: Retorna todas as vagas cadastradas no sistema.

Resposta:

```json
[
  {
    "id": 1,
    "cpfResponsavel": "12345678901",
    "titulo": "Cuidador para criança especial",
    "descricao": "Acompanhamento no período escolar.",
    "cidade": "São Paulo",
    "estado": "SP",
    "tipoDependente": "CRIANCA",
    "idadeDependente": 7,
    "doencaDiagnosticada": "Autismo",
    "status": "ATIVA"
  }
]
```

3. Listar Vagas por CPF do Responsável
```
GET /api/vagas/responsavel/{cpfResponsavel}
```

Descrição: Retorna todas as vagas de um responsável específico (filtradas por CPF).

Exemplo de Requisição:
```
GET /api/vagas/responsavel/12345678901
```

4. Buscar Vaga por ID
```
GET /api/vagas/id/{id}
```

Descrição: Retorna os detalhes de uma vaga específica.

Exemplo de Requisição:
```
GET /api/vagas/id/1
```

5. Atualizar Vaga
```
PUT /api/vagas/{id}
```

Descrição: Atualiza os dados de uma vaga.

Exemplo de Payload:

```json
{
  "titulo": "Novo Título da Vaga",
  "descricao": "Descrição atualizada.",
  "dataHoraInicio": "2024-12-02T08:00:00",
  "dataHoraFim": "2024-12-02T12:00:00",
  "cidade": "Campinas",
  "estado": "SP",
  "tipoDependente": "IDOSO",
  "idadeDependente": 75,
  "doencaDiagnosticada": "Diabetes",
  "status": "ENCERRADA"
}
```

6. Excluir Vaga
```
DELETE /api/vagas/{id}
```
Descrição: Exclui uma vaga pelo ID.

### **Candidaturas**

1. Criar Candidatura
```
POST /api/candidaturas
```

Descrição: Endpoint para que cuidadores se candidatem a uma vaga.

Exemplo de Payload:

```json
{
  "vaga": {
    "id": 1
  },
  "cuidadorId": 69,
  "valorHora": 50.0,
  "mensagemEnvio": "Tenho experiência relevante na área.",
  "politicaPrivacidade": true
}
```

2. Listar Candidaturas por Vaga
```
GET /api/candidaturas/vaga/{vagaId}
```
Descrição: Retorna todas as candidaturas relacionadas a uma vaga.

3. Listar Candidaturas por Cuidador
```
GET /api/candidaturas/cuidador/{cuidadorId}
```
Descrição: Retorna todas as candidaturas feitas por um cuidador específico.

4. Excluir Candidatura
```
DELETE /api/candidaturas/{id}
```
Descrição: Remove uma candidatura específica pelo ID.

### **Cuidadores**

1. Cadastrar Cuidador
```
POST /api/cuidadores
```
Descrição: Endpoint para cadastrar cuidadores.

Exemplo de Payload:

```json
{
  "nome": "João Silva",
  "cpf": "98765432100",
  "email": "joao@gmail.com",
  "telefone": "11999999999"
}
```

### Estrutura do Projeto
- Controller: Gerencia as requisições e respostas HTTP.
- Service: Contém as regras de negócio da aplicação.
- Repository: Gerencia as interações com o banco de dados.
- Model: Define as entidades da aplicação.
- DTO: Facilita a comunicação entre o frontend e backend.

### Testes
1. Testes foram criados para todas as classes de Service e Controller utilizando o Mockito e Spring Boot Test (Coverage ~97%).
2. Cobertura de testes foi garantida com o Jacoco.
