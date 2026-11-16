# Arquitetura resumida

A aplicação utiliza uma arquitetura em camadas. A interface web (HTML, CSS e JavaScript) apresenta as três telas previstas: início, cadastro e consulta/gerenciamento. A interface chama a API REST do Spring Boot. Os controllers recebem as requisições, os DTOs validam os campos, a camada de serviço aplica as regras de negócio e os repositories acessam o banco H2 por meio do Spring Data JPA.

## Entidade principal: Informacao

- `id`: identificador gerado automaticamente.
- `categoria`: classificação do registro (até 60 caracteres).
- `titulo`: título para identificação e pesquisa (até 120 caracteres).
- `descricao`: conteúdo resumido (até 500 caracteres).
- `dataReferencia`: data relacionada à informação.
- `criadoEm`: data e hora de criação, preenchida automaticamente.

## Fluxo principal

1. O colaborador abre a tela inicial e escolhe cadastrar ou consultar.
2. No cadastro, informa categoria, título, descrição e data.
3. O navegador faz validações básicas e envia os dados à API.
4. O servidor valida novamente, aplica as regras e persiste o registro.
5. Na consulta, o usuário pesquisa por título ou filtra por categoria.
6. Na edição, o sistema carrega o registro e envia as alterações por `PUT`.

## Decisões e segurança

A persistência usa ORM e parâmetros vinculados, evitando concatenar entradas em comandos SQL. Os campos têm limites e validação no servidor. A interface escapa conteúdo textual antes de renderizá-lo. Os endpoints da API exigem autenticação; o encoder Argon2id é usado para hash da senha de demonstração. Em produção, HTTPS, gestão persistente de usuários, perfis e gestão de segredos são requisitos adicionais.

## Responsabilidades das camadas

- **Interface web:** apresenta as funcionalidades ao usuário, coleta os dados e exibe os resultados das operações.
- **Controller:** recebe as requisições HTTP, direciona as operações e retorna as respostas da API.
- **DTO:** define os dados recebidos e aplica validações para evitar informações inválidas.
- **Service:** concentra as regras de negócio, organiza as operações de cadastro, consulta e atualização.
- **Repository:** realiza o acesso aos dados por meio do Spring Data JPA.
- **Model:** representa a entidade Informacao e seus atributos persistidos no banco H2.

## Comunicação entre as camadas

A comunicação ocorre por meio de requisições HTTP entre a interface e a API REST. O controller encaminha os dados ao service, que executa as regras necessárias e utiliza o repository para acessar o banco de dados. O resultado retorna pelo mesmo fluxo até ser apresentado na interface.

Essa separação facilita a manutenção, a identificação de erros e a evolução independente das partes do sistema.
