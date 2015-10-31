<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:interna>
    <script type="text/javascript">
        urlDownload = function (link) {
            link.href = "modelo12/download?ano=" + $("#ano").val() + "&mes=" + $("#mes").val();
        }
    </script>
    <form>
        <table class="form">
            <tr>
                <td>Ano</td>
                <td><input type="text" name="ano" id="ano" value="${ano}"/></td>
            </tr>
            <tr>
                <td>Mês</td>
                <td><input type="text" name="mes" id="mes" value="${mes}"/></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input type="submit" value="Buscar"/>
                    <a href="#" onclick="urlDownload(this)" target="_blank">Download</a>
                </td>
            </tr>
        </table>
    </form>

    <table class="tablesorter" id="listabens">
        <thead>
        <tr>
            <th>Código Contabil</th>
            <th>Interpretação</th>
            <th>Saldo em <fmt:formatDate value="${anterior.time}" pattern="dd/MM/yyyy"/></th>
            <th>Entradas</th>
            <th>Saídas</th>
            <th>Saldo em <fmt:formatDate value="${atual.time}" pattern="dd/MM/yyyy"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${codigos}" var="codigo">
            <tr>
                <td>${codigo.codigoContabil.codigo}</td>
                <td>${codigo.codigoContabil.descricao}</td>
                <td><fmt:formatNumber value="${codigo.saldoInicial}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${codigo.entradas}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${codigo.saidas}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${codigo.saldoFinal}" minFractionDigits="2" maxFractionDigits="2"/></td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="2">Total</td>
            <td><fmt:formatNumber value="${totalinicial}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td colspan="2">&nbsp;</td>
            <td><fmt:formatNumber value="${totalfinal}" minFractionDigits="2" maxFractionDigits="2"/></td>
        </tr>
        </tfoot>
    </table>
</t:interna>