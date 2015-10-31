<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<t:interna>
    <jsp:include page="menu.jsp"/>
    <sf:form method="post" modelAttribute="codigo" action="edita">
        <sf:hidden path="id"/>
        <table class="form">
            <tr>
                <td>Código</td>
                <td><sf:input path="codigo"/></td>
                <td><sf:errors path="codigo"/></td>
            </tr>
            <tr>
                <td>Descrição</td>
                <td><sf:input path="descricao"/></td>
                <td><sf:errors path="descricao"/></td>
            </tr>
            <tr>
                <td>
                    &nbsp;
                </td>
                <td colspan="2">
                    <input type="submit" value="Salvar" id="enviar"/>
                </td>
            </tr>
        </table>

    </sf:form>

</t:interna>