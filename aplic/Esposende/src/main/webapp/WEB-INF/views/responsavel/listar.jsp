<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:interna>
    <jsp:include page="menu.jsp"/>
    <table class="tablesorter">
        <thead>
        <tr>
            <th>Matrícula</th>
            <th>Nome</th>
            <th>Editar</th>
            <th>Excluir</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="responsavel" items="${responsaveis}">
            <tr>
                <td>${responsavel.matricula}</td>
                <td>${responsavel.nome}</td>
                <td><a href="editar?id=${responsavel.id}">Editar</a></td>
                <td><a
                        onclick="return confirm('Deseja excluir este responsável?')"
                        href="excluir/${responsavel.id}">Excluir</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <t:pager/>
</t:interna>
