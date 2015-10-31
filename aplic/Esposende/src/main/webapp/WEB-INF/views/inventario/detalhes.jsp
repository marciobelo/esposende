<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:interna>

    <table>
        <tr>
            <td>Responsável:</td>
            <td>${inventario.responsavel.nome}</td>
        </tr>
        <tr>
            <td>Emissão:</td>
            <td><fmt:formatDate value="${inventario.dataEmissao}" pattern="dd/MM/yyyy"/></td>
        </tr>
        <tr>
            <td>Conclusão:</td>
            <td><fmt:formatDate value="${inventario.dataFechamento}" pattern="dd/MM/yyyy"/></td>
        </tr>
        <tr>
            <td>Protocolo:</td>
            <td>${inventario.protocolo.seq}/${inventario.protocolo.ano}</td>
        </tr>
        <tr>
            <td><a href="relatorio/${inventario.id}">Download</a></td>
            <td>&nbsp;</td>
        </tr>

        <tr>
            <td colspan="2">
                <t:visualizaMiniaturas documentosDigital="${inventario.relatorioAssinado}" classe="Inventario"
                                       id="${inventario.id}" editavel="${!inventario.encerrado}"/>
            </td>
        </tr>

        <c:if test="${!inventario.encerrado}">
            <tr>
                <td>
                    <form method="post" action="encerrar">
                        <input type="hidden" name="inventario" value="${inventario.id}"/>
                        <input type="submit" value="Encerrar"/>
                    </form>
                </td>
                <td></td>
            </tr>
        </c:if>


        <c:if test="${aviso != null}">
            <tr>
                <td colspan="2"><span class="popup">${aviso}</span></td>
            </tr>
        </c:if>

    </table>

    <table class="tablesorter">
        <thead>
        <tr>
            <th>Código</th>
            <th>Bem</th>
            <th>Origem</th>
            <th>Tombamento</th>
            <th>Local</th>
            <th>Foto</th>
            <th>Situação</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${inventario.conferidos}" var="confere">
            <tr>
                <td>${confere.bemPermanente.id}</td>
                <td>${confere.bemPermanente.descricao}</td>
                <td>${confere.bemPermanente.origem.resumo}</td>
                <td>${confere.bemPermanente.tombamento.codTombamento}</td>
                <td>${confere.bemPermanente.localPermanencia.nome}</td>
                <td>
                    <c:choose>
                        <c:when test="${confere.bemPermanente.fotos[0] != null}">
                            <img class="imagemMiniatura"
                                 src="/Esposende/documentoDigital/${confere.bemPermanente.fotos[0].id}"
                        </c:when>
                        <c:otherwise>
                            <img class="imagemMiniatura"
                                 src="${pageContext.request.contextPath}/resources/sem_imagem.gif"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${!inventario.encerrado}">
                            <select onchange="mudaStatus('${pageContext.request.contextPath}/inventario/confere/${confere.id}/'+this.value)">
                                <option value="">Situação...</option>
                                <c:forEach items="${situacoes}" var="situacao">
                                    <option value="${situacao}"
                                            <c:if test="${situacao == confere.situacao}">selected</c:if>>${situacao.descricao}</option>
                                </c:forEach>
                            </select>
                        </c:when>
                        <c:otherwise>
                            ${confere.situacao.descricao}
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
    <t:pager/>
</t:interna>