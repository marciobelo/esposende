<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<t:interna>
    <jsp:include page="menu.jsp"/>
    <sf:form method="post" modelAttribute="origem">
        <sf:hidden path="id"/>
        <table class="form">
            <tr>
                <td>Resumo</td>
                <td><sf:input path="resumo"/></td>
                <td><sf:errors path="resumo"/></td>
            </tr>
            <tr>
                <td>Detalhes</td>
                <td><sf:input path="detalhe"/></td>
                <td><sf:errors path="detalhe"/></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input type="submit" value="Enviar" id="enviar"/></td>
                <td>&nbsp;</td>
            </tr>
        </table>

    </sf:form>

</t:interna>

