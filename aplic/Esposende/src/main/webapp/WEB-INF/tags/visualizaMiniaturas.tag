<%@ tag body-content="empty" language="java" %>
<%@ attribute name="documentosDigital" required="true" type="java.util.Collection" rtexprvalue="true" %>
<%@ attribute name="classe" required="true" type="java.lang.String" rtexprvalue="false" %>
<%@ attribute name="id" required="true" type="java.lang.Long" rtexprvalue="true" %>
<%@ attribute name="editavel" required="true" type="java.lang.Boolean" rtexprvalue="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="visualizaMiniaturas${classe}" class="visualizaMiniaturas"></div>

<c:if test="${editavel}">
    <a href="#" onclick="abreJanela('novoAnexo${classe}');" class="linkMiniatura">Adicionar</a>

    <a href="#" onclick="excluir${classe}Anexo();" class="linkMiniatura">Excluir</a>
</c:if>

<div id="imagemAmpliada" class="dialog">
    <iframe src="" id="fotoAmpliada" style="height: 500px; width: 650px;"></iframe>
</div>

<c:if test="${editavel}">
    <div id="novoAnexo${classe}" class="dialog">
        <iframe src="${pageContext.request.contextPath}/documentoDigital/formUpload/${classe}/${id}"></iframe>
    </div>
</c:if>

<c:if test="${!editavel}">
    <style type="text/css">
        input[name=${classe}Anexo] {
            display: none;
        }
    </style>
</c:if>

<script type="text/javascript">
    janela('imagemAmpliada', null);
    janela("novoAnexo${classe}", carrega${classe}Imagens);

    function carrega${classe}Imagens() {
        $.get("${pageContext.request.contextPath}/documentoDigital/listar/${classe}/${id}",
                function (data) {
                    $("#visualizaMiniaturas${classe} div.scrollableArea").html(data);
                });
    }

    function excluir${classe}Anexo() {
        var id = document.getElementById("${classe}Anexo");
        if ($('input[name=${classe}Anexo]:checked').length == 0) {
            window.alert("Selecione o anexo para excluir");
        } else {
            var idDocumentoDigital = $('input[name=${classe}Anexo]:checked').val();
            $.post("${pageContext.request.contextPath}/documentoDigital/excluir/${classe}/${id}/" + idDocumentoDigital,
                    function (data) {
                        carrega${classe}Imagens();
                    });
        }
    }

    $(document).ready(function () {
        carrega${classe}Imagens();
        $("#visualizaMiniaturas${classe}").smoothDivScroll();
    });
</script>