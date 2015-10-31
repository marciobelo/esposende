<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    function confirmarExclusao(id, codigo) {
        var ret = window.confirm( "Confirma a exclusão do código contábil " + codigo + "?" );
        if( ret ) {
            $("#idExcluir").val( id );
            $("#formExcluir").submit();
        }
    }
</script>

<t:interna>
    <jsp:include page="menu.jsp"/>
    <table class="tablesorter">
        <thead>
        <tr>
            <th>Código</th>
            <th>Descrição</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${codigos}" var="codigo">
            <tr>
                <td>${codigo.codigo}</td>
                <td>${codigo.descricao}</td>
                <td>
                <a href="edita?codigo=${codigo.id}">Editar</a>
                &nbsp;
                <a href="#" onclick="confirmarExclusao('${codigo.id}','${codigo.codigo}');">Excluir</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form id="formExcluir" action="${pageContext.request.contextPath}/codigocontabil/excluir" method="POST">
        <input type="hidden" id="idExcluir" name="id" value="" />
    </form>

    <t:pager></t:pager>

</t:interna>