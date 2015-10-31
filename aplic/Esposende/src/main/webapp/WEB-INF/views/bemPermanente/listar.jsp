<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:interna>
    <jsp:include page="menu.jsp"/>
	<script type="text/javascript">
    	inicializa();

    	$(document)
    			.ready(
    					function() {
    						$('#descricaoBem')
    								.autocomplete(
    										{
    											source : "${pageContext.request.contextPath}/bempermanente/autocomplete/descricao/",
    											minLength : 2
    										});
    						$('#nomeEmpresa')
    								.autocomplete(
    										{
    											source : "${pageContext.request.contextPath}/bempermanente/autocomplete/empresa/",
    											minLength : 2
    										});
    						$('#documentoAquisicao')
    								.autocomplete(
    										{
    											source : "${pageContext.request.contextPath}/bempermanente/autocomplete/documento/",
    											minLength : 2
    										});
    						$('#codigoTombamento')
    								.autocomplete(
    										{
    											source : "${pageContext.request.contextPath}/bempermanente/autocomplete/tombamento/",
    											minLength : 2
    										});
    					});

    </script>
    <div>
    <!-- hr class="secao" /-->
    <form id="criteriosBusca" method="post"
    	action="${pageContext.request.contextPath}/bempermanente/pesquisa/relatorio2">
    	<table class="fontesPequenas form">
    		<tr>
    			<td>Descrição</td>
    			<td><input type="text" name="descricaoBem" id="descricaoBem" /></td>

    			<td><span class="legenda">Código Tombamento</span></td>
    			<td><input type="text" name="codigoTombamento"
    				id="codigoTombamento" /></td>

    			<td><span class="legenda">Origem</span> </td>
    			<td><select name="origem" id="origem">
    					<option value="">Todas</option>
    					<c:forEach var="origem" items="${origens}">
    						<option value="${origem.id}">${origem.resumo}</option>
    					</c:forEach>
    			</select>
                </td>
    		</tr>
    		<tr>
    			<td>Responsável</td>
    			<td><select name="responsavel" id="responsavel">
    					<option value="">Todos</option>
    					<c:forEach var="responsavel" items="${responsaveis}">
    						<option value="${responsavel.id}">${responsavel.nome}</option>
    					</c:forEach>
    			</select></td>

                <td><span class="legenda">Local</span></td>
                <td><select name="local" id="local">
                    <option value="">Todos</option>
                    <c:forEach var="local" items="${locais}">
                        <option value="${local.id}">${local.nome}</option>
                    </c:forEach>
                </select></td>

    			<td><input type="button"
    				onclick="buscaBensPorCriterios('${pageContext.request.contextPath}/bempermanente/pesquisa');"
    				value="Buscar" />
                    </td>
                <td><input type="button" value="Impressão" onclick="impressao();" /></td>
    		</tr>
    	</table>
    </form>
    </div>
    <hr class="secao" />
    <div id="resultadoBusca" class="print"></div>
</t:interna>
