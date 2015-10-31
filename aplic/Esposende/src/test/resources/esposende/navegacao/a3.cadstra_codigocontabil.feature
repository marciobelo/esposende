# language: pt

Funcionalidade: Cadastrar um codigo contabil

  Cenário: Cadastrando codigo contabil com dados válidos
    Dado que estou em codigocontabil/edita
    Quando informo 10.3.4.5 no campo codigo
    E informo Aparelhos eletrônicos no campo descricao
    E clico em enviar
    Então devo ser redirecionado para codigocontabil/lista