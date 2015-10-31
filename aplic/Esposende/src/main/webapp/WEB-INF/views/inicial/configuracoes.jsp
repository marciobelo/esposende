<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:interna>

    <form method="post">
        <table class="form">
            <tr>
                <td>Responsável Institucional</td>
                <td>
                    <select name="responsavel">
                        <option value=""></option>
                        <c:forEach items="${responsaveis}" var="resp">
                            <option value="${resp.id}"
                                    <c:if test="${configuracoes.responsavelInstitucional.id == resp.id}">selected</c:if>>${resp.nome}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input type="submit" value="Salvar"/></td>
            </tr>

        </table>
    </form>

</t:interna>