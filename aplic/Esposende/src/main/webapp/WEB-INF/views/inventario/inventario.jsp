<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<table>
    <tr>
        <td>Nº de Inventário</td>
        <td>${inventario.protocolo.seq}/${inventario.protocolo.ano}</td>
    </tr>
    <tr>
        <td>Data de Emissão:</td>
        <td><fmt:formatDate value="${inventario.dataEmissao}" pattern="dd/MM/yyyy"/></td>
    </tr>
    <tr>
        <td>Data de Fechamento:</td>
        <td><fmt:formatDate value="${inventario.dataFechamento}" pattern="dd/MM/yyyy"/></td>
    </tr>
</table>

<table>
    <tr><th>Id</th></tr>
    <c:forEach items="${inventario.conferidos}" var="confere">
        <tr><td>${confere.id}</td></tr>
    </c:forEach>
</table>
