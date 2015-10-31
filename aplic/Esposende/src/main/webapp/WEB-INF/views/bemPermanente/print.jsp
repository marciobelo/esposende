<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/impressao.css" type="text/css"/>
</head>
<body style="width: 800px;">

<h3>Relação de Bens</h3>

<table>
    <tr>
        <td>Descrição:</td>
        <td class="espacado">${descricao}</td>
        <td>Tombamento:</td>
        <td class="espacado">${tombamento}</td>
    </tr>
    <tr>
        <td>Origem:</td>
        <td class="espacado">${origem}</td>
        <td>Responsável:</td>
        <td class="espacado">${responsavel}</td>
    </tr>
    <tr>
        <td>Local:</td>
        <td class="espacado">${local}</td>
    </tr>
</table>

<table class="impressao">
    <thead>
    <tr>
        <th colspan="8">
            Bem
        </th>
        <th colspan="3">
            Inventário
        </th>
        <th>
            Sub Rogo
        </th>
        <th>
            Baixa
        </th>
    </tr>
    <tr>
        <th>Código Interno</th>
        <th>Descrição</th>
        <th>Responsável</th>
        <th>Origem de Aquisição</th>
        <th>Local</th>
        <th>Código de Tombamento</th>
        <th>Data de Tombamento</th>
        <th>Foto</th>
        <th>Inventário</th>
        <th>Responsável</th>
        <th>Data</th>
        <th>Emissão</th>
        <th>Situação</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="bem" items="${bens}">
        <tr>
            <td>${bem[0].id}</td>
            <td>${bem[0].descricao}</td>
            <td>${bem[0].responsavel.nome}</td>
            <td>${bem[0].origem.resumo}</td>
            <td>${bem[0].localPermanencia.nome}</td>
            <td>${bem[0].tombamento.codTombamento}</td>
            <td><fmt:formatDate value="${bem[0].tombamento.dataTombamento}" pattern="dd/MM/yyyy"/></td>
            <td>
                <c:choose>
                    <c:when test="${bem[0].fotos[0] != null}">
                        <img style="height:50px;"
                             src="${pageContext.request.contextPath}/bempermanente/bem/foto/${bem[0].id}"/>
                    </c:when>
                    <c:otherwise>
                        Sem Foto
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${bem[1] != null}">
                        ${bem[1].protocolo}
                    </c:when>
                    <c:otherwise>
                        Não inventariado
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${bem[1].responsavel.nome}</td>
            <td>
                <fmt:formatDate value="${bem[1].dataFechamento}" pattern="dd/MM/yyyy"/>
            </td>
            <td>
                <fmt:formatDate value="${bem[2].dataEmissao}" pattern="dd/MM/yyyy"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${bem[0].baixa == null}">

                    </c:when>
                    <c:otherwise>
                        <c:if test="${bem[0].baixa.dataBaixaContabil == null}">
                            Em baixa desde <fmt:formatDate value="${bem[0].baixa.dataCriacao}" pattern="dd/MM/yyyy"/>
                        </c:if>
                        <c:if test="${bem[0].baixa.dataBaixaContabil != null}">
                            Baixado desde <fmt:formatDate value="${bem[0].baixa.dataBaixaContabil}"
                                                          pattern="dd/MM/yyyy"/>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="6">Gerado em <fmt:formatDate value="${data}" pattern="dd/MM/yyyy"/></td>
        <td colspan="7" style="text-align: right;">Esposende</td>
    </tr>
    </tfoot>
</table>
</body>
</html>