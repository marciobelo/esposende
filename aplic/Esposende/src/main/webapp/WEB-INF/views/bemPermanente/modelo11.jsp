<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:interna>
    <jsp:include page="menu.jsp"/>
    <form>
        <table class="form">
            <tr>
                <td>Ano</td>
                <td><input type="text" id="ano" name="ano" value="${param.ano}"/></td>
            </tr>
            <tr>
                <td>Mês</td>
                <td><input type="text" name="mes" id="mes" value="${param.mes}"/></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <input type="submit" value="Buscar"/>
                    <a href="modelo11/download?ano=${ano}&mes=${mes}" target="_blank">Download</a>
                </td>
            </tr>
        </table>
    </form>

    <table class="tablesorter" id="listabens">
        <thead>
        <tr>
            <th rowspan="2">Código do Plano de Contas</th>
            <th rowspan="2">Número de Inventariação</th>
            <th>Característica de Identificação</th>
            <th rowspan="2">Unid. de Medida</th>
            <th rowspan="2">Quant.</th>
            <th colspan="2">Valor</th>
            <th rowspan="2">Observaçoes</th>
        </tr>
        <tr>
            <th>Incorporações</th>
            <th>Unitário</th>
            <th>Global</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="bem" items="${bens}">
            <tr>
                <td>${bem.tombamento.codigoContabil.codigo}</td>
                <td>${bem.tombamento.codTombamento}</td>
                <td>${bem.descricao}</td>
                <td>UM</td>
                <td>1</td>
                <td>${bem.tombamento.valorOperacao}</td>
                <td>${bem.tombamento.valorOperacao}</td>
                <td>
                        ${bem.tombamento.documentoHabil} ${bem.tombamento.historicoOperacao}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <jsp:include page="../partes/pager.jsp"/>
</t:interna>