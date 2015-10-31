# language: pt

Funcionalidade: Cadastrar um local

  Cenário: Cadastrando um local novo
    Dado que estou em local/edit
    Quando informo Diretoria no campo nome
    E clico em enviar
    Então devo ser redirecionado para local/list