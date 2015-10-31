<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fieldset>
<legend>Básico</legend>
<table>
    <tr>
        <td>Descrição</td>
        <td>${bem.descricao}</td>
    </tr>
    <tr>
        <td>Informações Adicionais</td>
        <td>${bem.informacaoAdicional}</td>
    </tr>
    <tr>
        <td>Valor de Aquisição</td>
        <td>${bem.valorAquisicao}</td>
    </tr>
    <tr>
        <td>Data de Aquisição</td>
        <td><fmt:formatDate value="${bem.dataAquisicao}" pattern="dd/MM/yyyy" /></td></tr>
    <tr>
        <td>Origem</td>
        <td>${bem.origem.resumo}</td>
    </tr>
    <tr>
        <td>Local de Permanência</td>
        <td>${bem.localPermanencia.nome}</td>
    </tr>
</table>
</fieldset>

<fieldset>
<legend>Tombamento</legend>
<table>
    <tr><td>Nº Tombamento</td><td>${bem.tombamento.codTombamento}</td></tr>
    <tr><td>Nº Tombamento</td><td><fmt:formatDate value="${bem.tombamento.dataTombamento}" pattern="dd/MM/yyyy" /></td></tr>
</table>
</fieldset>