<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/impressao.css" type="text/css"/>
</head>
<body>
<h2>${titulo}</h2>
<table class="impressao">
    <thead>
    <tr>
        <th>Descrição</th>
        <th>Responsável</th>
        <th>Origem</th>
        <th>Código de Tombamento</th>
        <th>Data de Tombamento</th>
        <th>${tipo}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${bens}" var="bem">
        <tr>
            <td>${bem.bem.descricao}</td>
            <td>${bem.bem.responsavel.nome}</td>
            <td>${bem.bem.origem.resumo}</td>
            <td>${bem.bem.tombamento.codTombamento}</td>
            <td><fmt:formatDate value="${bem.bem.tombamento.dataTombamento}" pattern="dd/MM/yyyy"/></td>
            <td>${bem.detalhes}</td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="5">Gerado em <fmt:formatDate value="${data}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
        <td colspan="2">Esposende</td>
    </tr>
    </tfoot>
</table>
</body>
</html>