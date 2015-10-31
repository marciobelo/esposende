<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:interna>
    <jsp:include page="menu.jsp"/>
    <table class="tablesorter">
        <thead>
        <tr>
            <th>Resumo</th>
            <th>Detalhe</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="origem" items="${origens}">
            <tr>
                <td>${origem.resumo}</td>
                <td>${origem.detalhe}</td>
                <td><a href="editar/${origem.id}">Editar</a></td>
                <td><a
                        onclick="return confirm('Deseja excluir esta origem?')"
                        href="excluir/${origem.id}">Excluir</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <t:pager/>
</t:interna>