# language: pt

Funcionalidade: Cadastrar uma origem

  Cenário: Cadastrando origem com dados válidos
    Dado que estou em origem/novo
    Quando informo Compra no campo resumo
    E informo Bem adquirido com recursos próprios no campo detalhe
    E clico em enviar
    Então devo ser redirecionado para origem/listar