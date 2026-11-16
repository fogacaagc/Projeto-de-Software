
# Registro de Validação do MVP

## Testes automatizados

Foi executado o comando `mvn clean test` para verificar a compilação e a execução dos testes automatizados do projeto.

## Resultado obtido

- Resultado da compilação: BUILD SUCCESS.
- Testes executados: 1.
- Falhas: 0.
- Erros: 0.
- Testes ignorados: 0.

## Verificações funcionais previstas

Além dos testes automatizados, foram definidos cenários para verificar o cadastro, a consulta, a pesquisa por título, o filtro por categoria e a edição de informações.

## Considerações finais

O resultado positivo dos testes automatizados demonstra que o cenário coberto pela suíte de testes foi executado sem falhas. As demais verificações funcionais devem ser realizadas durante a validação manual do MVP.

## Como reproduzir os testes

Para executar os testes automatizados, é necessário ter o JDK e o Maven instalados.

No terminal, dentro da pasta principal do projeto, execute:

```bash
mvn clean test
```

O Maven irá compilar o projeto e executar os testes disponíveis na suíte automatizada.

Ao final, o resultado poderá ser conferido no terminal, incluindo a quantidade de testes executados, falhas e erros.
