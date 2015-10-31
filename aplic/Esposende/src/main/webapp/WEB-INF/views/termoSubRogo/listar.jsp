<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:interna>

    <sf:form action="listar" method="GET" modelAttribute="termoSubRogoModel">

            Respons&aacute;vel:
            <sf:select path="idResponsavel" onchange="document.getElementById('termoSubRogoModel').submit();">
                <option value="">Todos</option>
                <sf:options items="${termoSubRogoModel.responsaveis}" itemValue="id" itemLabel="nome"/>
            </sf:select>

    </sf:form>

    <hr class="secao"/>

    <br/>

    <table class="tablesorter">
        <thead>
        <tr>
            <th>Protocolo</th>
            <th>Responsável</th>
            <th>Dt.Emissão</th>
            <th>Dt.SubRogo</th>
            <th>Dt.Prevista de Encerramento</th>
            <th>Dt.Encerramento</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${termoSubRogoModel.termosPesquisados}" var="termo">
            <tr>
                <td>${termo.protocolo}</td>
                <td>${termo.subrogado.nome}</td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${termo.dataEmissao}"/></td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${termo.dataSubRogo}"/></td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${termo.dataPrevistaEncerramento}"/></td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${termo.dataEncerramento}"/></td>
                <td><a href="${pageContext.request.contextPath}/termoSubRogo/exibir?termo=${termo.id}">Visualizar</a></td>
                <td><a target="_blank" href="download?termo=${termo.id}">Download</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <t:pager/>

    <hr class="secao"/>

    <%-- botao novo subrogo --%>
    <form action="${pageContext.request.contextPath}/termoSubRogo/editar" method="GET">
        <input type="submit" value="Novo SubRogo"/>
    </form>

</t:interna>