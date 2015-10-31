<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:interna>
    <jsp:include page="menu.jsp"/>

    <script type="text/javascript">
        function confirmarExcluirLocalPermanencia(id, nome ) {
            if( window.confirm('Deseja excluir o local ' + nome + " ?") ) {
                $("#idExcluir").val( id );
                $("#formExcluir").submit();
            }
        }
    </script>

    <table class="tablesorter">
        <thead>
        <tr>
            <th>Nome</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="local" items="${locais}">
            <tr>
                <td>${local.nome}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/local/edit?localPermanencia=${local.id}">Editar</a>
                </td>
                <td>
                    <a href="#" onclick="confirmarExcluirLocalPermanencia( '${local.id}', '${local.nome}' );">Excluir</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form id="formExcluir" action="${pageContext.request.contextPath}/local/delete" method="POST">
        <input type="hidden" id="idExcluir" name="id" value="" />
    </form>

    <t:pager/>

</t:interna>