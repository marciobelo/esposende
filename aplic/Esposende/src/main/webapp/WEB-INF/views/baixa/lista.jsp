<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:interna>

    <jsp:include page="menu.jsp"/>

    <table class="tablesorter">
        <thead>
        <tr>
            <th>Protocolo</th>
            <th>Criação</th>
            <th>Processo</th>
            <th>Data Processo</th>
            <th>Justificativa</th>
            <th>Data Termo</th>
            <th>Data Baixa Contabil</th>
            <th>Quantidade de Bens</th>
            <th>Relatório</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${baixas}" var="baixa">
            <tr>
                <td>${baixa.protocolo}</td>
                <td><fmt:formatDate value="${baixa.dataCriacao}" pattern="dd/MM/yyyy"/></td>
                <td>${baixa.numeroProcesso}</td>
                <td><fmt:formatDate value="${baixa.dataProcesso}" pattern="dd/MM/yyyy"/></td>
                <td>${baixa.justificativa}</td>
                <td><fmt:formatDate value="${baixa.dataTermoBaixa}" pattern="dd/MM/yyyy"/></td>
                <td><fmt:formatDate value="${baixa.dataBaixaContabil}" pattern="dd/MM/yyyy"/></td>
                <td>${baixa.numeroBens}</td>
                <td>
                    <c:if test="${baixa.dataBaixaContabil != null}">
                        <a href="download/${baixa.id}" target="_blank">Download</a>
                    </c:if>
                </td>
                <td><a href="termo?baixa=${baixa.id}">Detalhes</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</t:interna>