# WEC SSVP - Sistema de Gestão de Assistidos

## 📖 Sobre o Projeto
O **WEC SSVP** é uma API RESTful desenvolvida para gerenciar o cadastro de pessoas assistidas. O sistema visa facilitar o trabalho de administradores e voluntários, digitalizando informações sociais, financeiras, de moradia e de saúde dos assistidos.

A arquitetura do projeto foi desenhada para suportar diferentes perfis de usuário, permitindo que administradores realizem cadastros diretos e, futuramente, que voluntários enviem pré-cadastros para aprovação.

## 🚀 Tecnologias Utilizadas
* **Linguagem:** Java (utilizando *Records* para DTOs)
* **Framework:** Spring Boot 3
* **Persistência de Dados:** Spring Data JPA / Hibernate
* **Banco de Dados:** PostgreSQL
* **Gerenciamento de Dependências:** Maven
* **Padrões de Arquitetura:** Arquitetura de Camadas (Controller, Service, Repository, Model, DTO)

## ⚙️ Funcionalidades
### Atuais (MVP)
- [x] Modelagem de dados completa do Assistido (Dados pessoais, financeiros, moradia e saúde).
- [x] CRUD completo de Assistidos (Create, Read, Update, Delete) para o perfil Administrador.
- [x] Utilização de DTOs (Data Transfer Objects) para controle de entrada e saída de dados da API.

### Próximos Passos (Roadmap)
- [ ] Criação dos endpoints no `AssistidoController`.
- [ ] Implementação de sistema de **Aprovação de Cadastros**: Voluntários criam cadastros com status "Pendente", aguardando revisão do Administrador.
- [ ] Aplicação do padrão de projeto **Strategy** para isolar as lógicas de salvamento entre Administrador e Voluntário.
- [ ] Aplicação do padrão de projeto **Observer** para sistema de notificações (avisar o admin sobre novos cadastros pendentes).

## 📁 Estrutura do Projeto
O projeto segue a padronização de diretórios do Spring Boot:
```text
src/main/java/com/daniel/wec_ssvp/
 ├── controller/   # Controladores REST (Endpoints da API)
 ├── dto/          # Objetos de Transferência de Dados (Records)
 ├── exception/    # Tratamento global de erros e exceções customizadas
 ├── model/        # Entidades mapeadas para o banco de dados (JPA)
 ├── repository/   # Interfaces de comunicação com o banco (Spring Data)
 └── service/      # Regras de negócio da aplicação
```

🛠️ Como executar o projeto localmente
Pré-requisitos
Java 17 ou superior instalado.

Maven instalado.

PostgreSQL instalado e rodando na porta padrão (5432).

Passos para execução
Clone o repositório:

```text
git clone [https://github.com/seu-usuario/WEC_SSVP.git](https://github.com/seu-usuario/WEC_SSVP.git)
``` 

Crie um banco de dados no PostgreSQL chamado wec_ssvp.

Configure as credenciais do banco de dados no arquivo src/main/resources/application.properties:

```text
spring.datasource.url=jdbc:postgresql://localhost:5432/wec_ssvp
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
``` 