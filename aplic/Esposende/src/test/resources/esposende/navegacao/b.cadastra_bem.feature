# language: pt

Funcionalidade: Cadastrar um bem permanente

  Cenário: Cadastrando bem com dados válidos
    Dado que estou em bempermanente/novo
    Quando informo New iPad no campo descricao
    E informo 1800 no campo valorOperacao
    E informo 19/09/2013 no campo dataTombamento
    E informo 98769 no campo codigoTombamento
    E informo 12312 no campo documentoHabil
    E clico em enviaForm
    Então devo ver visualizaMiniaturasBemPermanente
    E devo ver visualizaMiniaturasTombamento