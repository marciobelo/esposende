<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:interna>
    <div>
        <p>
            Responsável Interno:
            <select onchange="buscaBensResponsavel('${pageContext.request.contextPath}/inventario/opcoes/'+this.value);"
                    id="responsavel">
                <option value="" selected="selected">Responsável</option>
                <c:forEach var="responsavel" items="${responsaveis}">
                    <option value="${responsavel.id}">${responsavel.nome}</option>
                </c:forEach>
            </select>
        </p>
    </div>
    <hr/>
    <div id="inventario"></div>
</t:interna>