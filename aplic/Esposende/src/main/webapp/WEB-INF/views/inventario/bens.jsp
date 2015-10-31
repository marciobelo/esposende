<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="inventario">
	<tr>
		<td>Id Interno</td>
		<td>Tombamento</td>
		<td>Descrição</td>
		<td>Foto</td>
		<td>Situação<br />Data Conferido</td>
		<td>Situação</td>
	</tr>
<c:forEach var="bem" items="${bens}">
	<tr style="height:60px;">
		<td>
            ${bem.id}
		</td>
		<td>
			${bem.tombamento.codTombamento}
		</td>
		<td>${bem.descricao}</td>
		<td>
            <c:choose>
            <c:when test="${bem.fotos[0] != null}">
				<img style="height:50px;" src="${pageContext.request.contextPath}/bempermanente/bem/foto/${bem.id}" />
			</c:when>
			<c:otherwise>
                 Sem Foto
			</c:otherwise>
			</c:choose>
		</td>
		<td>&nbsp;</td>
		<td>
			<select>
				<option>Situação...</option>
				<c:forEach var="situacao" items="${situacoes}">
            		<option value="${situacao}">${situacao.descricao}</option>
            	</c:forEach>
			</select>
		</td>
	</tr>
</c:forEach>
<table>