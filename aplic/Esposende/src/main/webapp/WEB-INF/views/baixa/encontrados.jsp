<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<table class="tablesorter">
    <thead>
    <th>Descrição</th>
    <th># Tombamento</th>
    <th>Responsável</th>
    <th>&nbsp;</th>
    <th>&nbsp;</th>
    </thead>
    <tbody>
    <c:forEach items="${encontrados}" var="bem">
        <tr>
            <td><c:out value="${bem[0].descricao}"/></td>
            <td><c:out value="${bem[0].tombamento.codTombamento}"/></td>
            <td><c:out value="${bem[0].responsavel.nome}"/></td>
            <td>
                <c:choose>
                    <c:when test="${bem[0].fotos[0] != null}">
                        <img src="/Esposende/documentoDigital/${bem[0].fotos[0].id}" class="imagemMiniatura"/>
                    </c:when>
                    <c:otherwise>
                        <img src="/Esposende/resources/sem_imagem.gif" class="imagemMiniatura"/>
                    </c:otherwise>
                </c:choose>
            </td>
                <%-- A função javascript do botão abaixo realiza chamada para o form onde
                este div estará contido, da janela termoSubRogo/form.jsp --%>
            <td>
                <c:if test="${bem[0].baixa == null && bem[1] == null}">
                    <input type="button" value="Adicionar" onclick="adicionarBem(${bem[0].id});"/>
                </c:if>
                <c:if test="${bem[0].baixa != null}">
                    Indisponível
                </c:if>
                <c:if test="${bem[1] != null}">
                    Subrogado
                </c:if>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>


<script type="text/javascript">
    $(".tablesorter")
            .tablesorter({widthFixed: true, widgets: ['zebra']});
</script>