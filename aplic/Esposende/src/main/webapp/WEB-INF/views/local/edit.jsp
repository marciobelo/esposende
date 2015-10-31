<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:interna>
    <jsp:include page="menu.jsp"/>
    <sf:form action="edit" modelAttribute="localPermanencia" method="post">
        <sf:hidden path="id"/>
        <table class="form">
            <tr>
                <td>Nome</td>
                <td><sf:input path="nome"/></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input type="submit" value="Enviar" id="enviar"/></td>
            </tr>
        </table>

    </sf:form>
</t:interna>