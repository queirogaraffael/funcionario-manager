# Gerenciador de Funcionários

Este projeto oferece uma solução robusta para o gerenciamento de dados de funcionários. Ele é construído com uma clara separação de responsabilidades, utilizando Objetos de Transferência de Dados (DTOs), exceções personalizadas e uma camada de serviço para lidar com a lógica de negócios.

## Visão Geral do `FuncionarioService`

A classe `FuncionarioService` é o cerne da lógica de negócios para o gerenciamento de funcionários. Ela atua como um intermediário entre os controladores e a camada de acesso a dados (repositórios), garantindo a integridade dos dados e aplicando as regras de negócio.

### Principais Funcionalidades:

* **Criação de Funcionários:** Permite a criação de novos registros de funcionários, garantindo que não sejam criados funcionários duplicados com base no CPF. Também lida com a associação de um endereço ao funcionário.
* **Recuperação de Funcionários:** Oferece diversas formas de recuperar informações de funcionários:
    * Recuperação de todos os funcionários com **paginação**.
    * Recuperação de um único funcionário pelo seu CPF.
    * Busca de funcionários por nome com **paginação**.
    * Busca de funcionários por cargo com **paginação**.
    * Busca de funcionários por cidade com **paginação**.
* **Atualização de Funcionários:** Permite a modificação de detalhes de funcionários existentes usando seu CPF.
* **Exclusão de Funcionários:** Suporta a remoção de registros de funcionários pelo CPF.
* **Gerenciamento Transacional:** Todas as operações que modificam dados são encapsuladas em transações para garantir a consistência e atomicidade dos dados. Operações somente leitura também são explicitamente marcadas para otimização.
* **Tratamento de Erros:** Exceções personalizadas são usadas para fornecer mensagens de erro significativas para cenários como um funcionário já existente ou um funcionário não encontrado.

### Objetos de Transferência de Dados (DTOs) Utilizados:

* `FuncionarioRequestDTO`: Usado para receber dados de funcionários durante a criação.
* `FuncionarioResponseDTO`: Usado para enviar dados de funcionários como resposta, geralmente contendo um subconjunto de atributos do funcionário.
* `FuncionarioUpdateDTO`: Usado para receber dados de funcionários atualizados.

## Dependências do Projeto

As seguintes dependências foram utilizadas neste projeto:

* **Spring Boot Starter Data JPA:** Para interação com bancos de dados relacionais usando a API JPA.
* **Spring Boot Starter Web:** Para construir aplicações web, incluindo o servidor Tomcat incorporado.
* **H2 Database:** Um banco de dados em memória, utilizado para desenvolvimento e testes.
* **Lombok:** Uma biblioteca para reduzir código clichê (boilerplate) em classes Java (e.g., getters, setters, construtores).
* **Spring Boot Starter Test:** Para escrever e executar testes unitários e de integração.
* **SpringDoc OpenAPI Starter WebMVC UI:** Para gerar documentação OpenAPI (Swagger UI) automaticamente para os endpoints da API.
* **Spring Boot Starter Validation:** Para validação de dados de entrada usando anotações.

## Testes

Foi realizado um **smoke test** utilizando a ferramenta **k6** para validar a disponibilidade e o comportamento básico dos endpoints da API.