<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not inventarioAberto and temBensSobResponsabilidade }">
    <input type="button" value="Criar Novo Inventário"
           onclick="window.location = '${pageContext.request.contextPath}/inventario/cria/${responsavel.id}';"/>
</c:if>
<c:if test="${not temBensSobResponsabilidade}">
    <h3>Responsável sem bens sob responsabilidade</h3>
    <h4>Não pode abrir inventário</h4>
</c:if>


<table class="tablesorter">
    <thead>
    <tr>
        <th>Número</th>
        <th>Data Início</th>
        <th>Data Conclusão</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${inventarios}" var="inventario">
        <tr>
            <td>${inventario.protocolo}</td>
            <td><fmt:formatDate value="${inventario.dataEmissao}" pattern="dd/MM/yyyy"/></td>
            <td><fmt:formatDate value="${inventario.dataFechamento}" pattern="dd/MM/yyyy"/></td>
            <td><a href="${pageContext.request.contextPath}/inventario/${inventario.id}">Detalhes</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<t:pager/>

<script type="text/javascript">
    $(document).ready(function () {
        $(".tablesorter")
                .tablesorter({widthFixed: true, widgets: ['zebra']})
                .tablesorterPager({container: $("#pager")});
    });
</script>