<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="tablesorter" id="listabens">
    <thead>
    <tr>
        <th colspan="8">
            Bem
        </th>
        <th colspan="3">
            Inventário
        </th>
        <th>
            Sub Rogo
        </th>
        <th>
            Baixa
        </th>
        <th colspan="3">
            &nbsp;
        </th>
    </tr>
    <tr>
        <th>Código Interno</th>
        <th>Descrição</th>
        <th>Responsável</th>
        <th>Origem de Aquisição</th>
        <th>Local</th>
        <th>Código de Tombamento</th>
        <th>Data de Tombamento</th>
        <th>Foto</th>
        <th>Inventário</th>
        <th>Responsável</th>
        <th>Data</th>
        <th>Emissão</th>
        <th>Situação</th>
        <th>Ficha</th>
        <th>FIBP</th>
        <th>Editar</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="bem" items="${bens}">
        <tr>
            <td>${bem[0].id}</td>
            <td>${bem[0].descricao}</td>
            <td>${bem[0].responsavel.nome}</td>
            <td>${bem[0].origem.resumo}</td>
            <td>${bem[0].localPermanencia.nome}</td>
            <td>${bem[0].tombamento.codTombamento}</td>
            <td><fmt:formatDate value="${bem[0].tombamento.dataTombamento}" pattern="dd/MM/yyyy"/></td>
            <td>
                <c:choose>
                    <c:when test="${bem[0].fotos[0] != null}">
                        <img style="height:50px;"
                             src="${pageContext.request.contextPath}/bempermanente/bem/foto/${bem[0].id}"/>
                    </c:when>
                    <c:otherwise>
                        Sem Foto
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${bem[1] != null}">
                        ${bem[1].protocolo}
                    </c:when>
                    <c:otherwise>
                        Não inventariado
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${bem[1].responsavel.nome}</td>
            <td>
                <fmt:formatDate value="${bem[1].dataFechamento}" pattern="dd/MM/yyyy"/>
            </td>
            <td>
                <fmt:formatDate value="${bem[2].dataEmissao}" pattern="dd/MM/yyyy"/>
            </td>
            <td>
                <c:if test="${bem[0].baixa != null}">
                    <c:if test="${bem[0].baixa.dataBaixaContabil == null}">
                        Em baixa desde <fmt:formatDate value="${bem[0].baixa.dataCriacao}" pattern="dd/MM/yyyy"/>
                    </c:if>
                    <c:if test="${bem[0].baixa.dataBaixaContabil != null}">
                        Baixado desde <fmt:formatDate value="${bem[0].baixa.dataBaixaContabil}"
                                                      pattern="dd/MM/yyyy"/>
                    </c:if>
                </c:if>
            </td>
            <td>
                <c:if test="${bem[0].tombamento != null}">
                    <a href="ficha/${bem[0].id}">Download</a>
                </c:if>
            </td>
            <td>
                <c:if test="${bem[0].tombamento != null}">
                    <a href="fibp/${bem[0].id}">FIBP</a>
                </c:if>
            </td>
            <td><a href="${pageContext.request.contextPath}/bempermanente/editar?bemPermanente=${bem[0].id}">Editar</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../partes/pager.jsp"/>
<div title="Foto" id="janelaFotoBem" style="display:none;"><img id="fotoBem" src=""/></div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#listabens")
                .tablesorter({widthFixed: true, widgets: ['zebra']})
                .tablesorterPager({container: $("#pager")});

        $(".janela").dialog({
            autoOpen: false,
            modal: true
        });

        $('.foto').click(function (event) {
            event.preventDefault();
            PreviewImage($(this).attr('href'), $(this).attr('title'));
        });
    });

</script>