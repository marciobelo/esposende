buscaBensPorCriterios = function (url) {
    $.post(url, $("#criteriosBusca").serialize(), function (data) {
        $("#resultadoBusca").html(data);
    });
}

buscaBensResponsavel = function (url) {
    $("#inventario").load(url);
}

criaNovoInventario = function (url) {
    $('#inventario').load(url);
}


detalhesBem = function (idBem, descricao) {
    $("#detalhesBem").load("detalhes/" + idBem, function () {
        $("#detalhesBem").dialog("open");
    });
}

PreviewImage = function (uri, title) {
    imageDialog = $("#janelaFotoBem");
    imageTag = $('#fotoBem');
    uriParts = uri.split("/");
    imageTag.attr('src', uri);
    imageTag.load(function () {
        $('#janelaFotoBem').dialog({
            modal: true,
            resizable: false,
            draggable: false,
            width: 'auto',
            title: title
        });
    });
}

janelaImagem = function (url) {
    $("#janelaImagem").dialog("open");
}

janela = function (id, callback) {
    $("#" + id).dialog({
        modal: true,
        resizable: true,
        draggable: false,
        autoOpen: false,
        buttons: {
            "Fechar": function () {
                $(this).dialog("close");
                if (callback != null) {
                    callback();
                }
            }
        },
        closeOnEscape: true,
        show: 'blind',
        hide: 'blind',
        width: 'auto'
    });
}

ajustaAlturaPagina = function () {
    var corpo = document.getElementById("corpo");
    var altura = window.innerHeight - 160;
    corpo.style.height = altura + "px";
}

buscaDocumento = function (url) {

    $.ajax({
        type: "GET",
        url: url,
        dataType: "xml",
        success: function (xml) {
            try {
                $("#nomeEmpresa").val(xml.getElementsByTagName("nomeEmpresa")[0].firstChild.nodeValue);
                $("#idDocumentoAquisicao").val(xml.getElementsByTagName("id")[0].firstChild.nodeValue);
            } catch (e) {
                $("#nomeEmpresa").val("");
                $("#idDocumentoAquisicao").val("");
            }
        },
        error: function () {
            alert("Erro!");
        }
    });
}

buscaTombamento = function (url) {
    $.ajax({
        type: "GET",
        url: url,
        dataType: "xml",
        success: function (xml) {
            var data = xml.getElementsByTagName("dataTombamento")[0].firstChild.nodeValue.split("-")
            $("#dataTombamento").val(data[2] + "/" + data[1] + "/" + data[0]);
            $("#idTombamento").val(xml.getElementsByTagName("id")[0].firstChild.nodeValue);
        },
        error: function () {
            alert("Erro!");
        }
    });
}

novoResponsavel = function (valor) {
    if (valor == "novo") {
        $("#novoResponsavel").dialog("open");
    }
}

novaOrigem = function (valor) {
    if (valor == "nova") {
        $("#novaOrigem").dialog("open");
    }
}

abreJanela = function (id) {
    $("#" + id).dialog("open");
}

enviaOrigem = function (url) {
    var form = document.getElementById("formNovaOrigem");

    $.post(url, $("#formNovaOrigem").serialize())
        .done(function (data) {
            $("#novaOrigem").dialog("close");
            form.reset();
            $("#avisoOrigem").html("");
            atualizaOrigens();
        })
        .fail(function () {
            $("#avisoOrigem").html("Verifique se todos os dados foram informados")
        });
}

atualizaOrigens = function () {
    $("#origem").load("/Esposende/origem/origens");
}

atualizaResponsaveis = function () {
    $("#responsavel").load("/Esposende/responsavel/responsaveis");
}

mudaStatus = function (url) {
    $.get(url);
}

verificaFormatoMoeda = function (input) {
    var valor = input.value;
    input.value = valor.replace('.', '');
}

ajustaCamposBemPermanente = function (form) {

    if (!form.valorAquisicao.contains(',')) {
        //valorAquisicao.value += ",00";
    }
}

abrePdfModelo11 = function () {
    $('#downloadmodelo11').attr('href', 'modelo11/download?ano=' + $('#ano').val() + '&mes=' + $('#mes').val());
}

marcaPaginaAtual = function () {
    $('.menu a').each(function () {
        if ($(this).attr('href') === window.location.pathname) {
            $(this).addClass('atual');
        }
    });
}

$(function ($) {
    $.datepicker.regional['pt-BR'] = {
        closeText: 'Fechar',
        prevText: '&#x3c;Anterior',
        nextText: 'Pr&oacute;ximo&#x3e;',
        currentText: 'Hoje',
        monthNames: [ 'Janeiro', 'Fevereiro', 'Mar&ccedil;o', 'Abril',
            'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro',
            'Novembro', 'Dezembro' ],
        monthNamesShort: [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
            'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
        dayNames: [ 'Domingo', 'Segunda-feira', 'Ter&ccedil;a-feira',
            'Quarta-feira', 'Quinta-feira', 'Sexta-feira',
            'S&aacute;bado' ],
        dayNamesShort: [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex',
            'S&aacute;b' ],
        dayNamesMin: [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex',
            'S&aacute;b' ],
        weekHeader: 'Sm',
        dateFormat: 'dd/mm/yy',
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''
    };
    $.datepicker.setDefaults($.datepicker.regional['pt-BR']);
});

abrePopup = function (url) {
    window.open(url, 'page', 'toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=800,height=800');
}

impressao = function () {
    var url = "pesquisa?"
        + "descricaoBem=" + $("#descricaoBem").val()
        + "&codigoTombamento=" + $("#codigoTombamento").val()
        + "&origem=" + $("#origem").val()
        + "&responsavel=" + $("#responsavel").val()
        + "&local=" + $("#local").val();
    abrePopup(url);
}

/*
function confirmarExcluirLocalPermanencia( id, nome ) {
    if window.confirm('Deseja excluir o local ' + nome + " ?") {
        $("#idExcluir").val( id );
        $("#formExcluir").submit();
    }
}
*/
