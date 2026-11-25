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
* **Arquitetura:** Hexagonal (Portas e Adaptadores), conforme a seção 4.5 do TG

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

## 📁 Estrutura do Projeto (Arquitetura Hexagonal)
O back-end segue a arquitetura hexagonal (Cockburn, 2005): o **núcleo** guarda as regras de negócio e não depende de nenhum framework; ele se comunica com o mundo externo por **portas** (interfaces), e os **adaptadores** implementam essas portas com Spring, JPA e JWT.

```text
src/main/java/com/daniel/wec_ssvp/
 ├── domain/                      # NÚCLEO - Java puro, sem Spring/JPA
 │    ├── model/                  #   Entidades de domínio (Assistido, Conferencia, ConselhoParticular, Usuario)
 │    └── exception/              #   Exceções de regra de negócio
 ├── application/                 # NÚCLEO - casos de uso
 │    ├── port/in/                #   PORTAS DE ENTRADA: interfaces dos casos de uso (CriarAssistidoUseCase...)
 │    ├── port/out/               #   PORTAS DE SAÍDA: o que o núcleo precisa de fora (repositórios, senha, token)
 │    └── usecase/                #   Implementação dos casos de uso (sem anotações do Spring)
 ├── adapter/                     # ADAPTADORES
 │    ├── in/web/                 #   Entrada: controllers REST, DTOs, mapper, tratamento de erros, filtro JWT
 │    └── out/
 │         ├── persistence/       #   Saída: entidades JPA, Spring Data e adaptadores que implementam as portas
 │         └── security/          #   Saída: JWT (GeradorTokenPort) e BCrypt (CodificadorSenhaPort)
 └── config/                      # Liga tudo: instancia os casos de uso (UseCaseConfig), segurança e CORS
```

**Regra de dependência:** `adapter` e `config` podem depender do núcleo; `domain` e `application` nunca importam nada de `adapter`, `config`, Spring ou JPA. Dá para conferir compilando o núcleo sozinho, sem nenhum jar no classpath:

```text
javac -d /tmp/nucleo $(find src/main/java/com/daniel/wec_ssvp/domain src/main/java/com/daniel/wec_ssvp/application -name "*.java")
```

**Fluxo de uma requisição**, por exemplo `POST /assistidos/criar`:
`AssistidoController` (adaptador de entrada) → `WebMapper` converte o DTO em `AssistidoCommand` → `CriarAssistidoUseCase` (porta de entrada) → `CriarAssistidoService` aplica a regra → `AssistidoRepositoryPort` (porta de saída) → `AssistidoPersistenceAdapter` salva com JPA.

### Testes
* `application/usecase/*Test` — testam os casos de uso com portas em memória (`PortasEmMemoria`), **sem banco e sem Spring**, como previsto no item 3.5 do TG.
* `adapter/in/web/ApiFluxoIntegrationTest` — teste ponta a ponta da API com MockMvc e H2 em memória.

```text
./mvnw clean test
```

> **Use sempre `clean` depois de apagar ou mover classes.** A compilação incremental do Maven não remove os `.class` antigos de `target/classes`; uma classe apagada continua no classpath e o Spring falha com `ConflictingBeanDefinitionException`.

## 🛠️ Como executar o projeto localmente
Pré-requisitos
Java 17 ou superior instalado.

Maven instalado.

PostgreSQL instalado e rodando na porta padrão (5432).

Passos para execução
Clone o repositório:

```text
git clone https://github.com/seu-usuario/WEC_SSVP.git
``` 

Crie um banco de dados no PostgreSQL chamado wec_ssvp.

Configure as credenciais do banco de dados no arquivo src/main/resources/application.properties:

```text
spring.datasource.url=jdbc:postgresql://localhost:5432/wec_ssvp
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
``` 
