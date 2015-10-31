# language: pt

Funcionalidade: Cadastrar um novo responsavel

  Cenário: Cadastrando responsavel com dados válidos
    Dado que estou em responsavel/editar
    Quando informo Ayrton Senna no campo nome
    E informo 1991 no campo matricula
    E informo ayrton@ist.edu.br no campo email
    E coloco uma imagem no campo fotoResponsavel
    E clico em enviar
    Então devo ser redirecionado para responsavel/listar