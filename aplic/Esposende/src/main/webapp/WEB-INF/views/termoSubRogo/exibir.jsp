<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:interna>

    <fieldset class="dados">
        <legend>Termo de SubRogo</legend>
        <table>
            <tbody>
            <tr>
                <td>Nº Protocolo:</td>
                <td>${termoSubRogoModel.protocolo}</td>
            </tr>
            <tr>
                <td>Responsável:</td>
                <td>${termoSubRogoModel.nomeResponsavel}</td>
            </tr>
            <tr>
                <td>Data Emissão:</td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${termoSubRogoModel.dataEmissao}"/></td>
            </tr>
            <tr>
                <td>Propósito:</td>
                <td>${termoSubRogoModel.proposito}</td>
            </tr>
            <tr>
                <td>Data SubRogo:</td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${termoSubRogoModel.dataSubRogo}"/></td>
            </tr>
            <tr>
                <td>Data Prevista Encerramento:</td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${termoSubRogoModel.dataPrevistaEncerramento}"/></td>
            </tr>
            <tr>
                <td>Data Encerramento:</td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${termoSubRogoModel.dataEncerramento}"/></td>
            </tr>
            <tr>
                <td><a target="_blank" href="../download?id=${termoSubRogoModel.id}">Download</a></td>
                <td>&nbsp;</td>
            </tr>
            </tbody>
        </table>

        <t:visualizaMiniaturas documentosDigital="${termoSubRogoModel.termosAssinados}"
                               classe="TermoSubRogo" id="${termoSubRogoModel.id}" editavel="false"/>

    </fieldset>

    <fieldset class="dados">
        <legend>Bens Selecionados</legend>
        <!-- tabela dos bens selecionados -->
        <table class="tablesorter">
            <thead>
            <th>Descrição</th>
            <th># Tombamento</th>
            <th>Responsável Atual</th>
            <th>Foto</th>
            </thead>
            <tbody>
            <c:forEach items="${termoSubRogoModel.idBensSelecionados}" var="idBemSelecionado">
                <c:set var="bemPermanente" value="${termoSubRogoModel.bens[idBemSelecionado]}"/>
                <tr>
                    <td><c:out value="${bemPermanente.descricao}"/></td>
                    <td><c:out value="${bemPermanente.tombamento.codTombamento}"/></td>
                    <td><c:out value="${bemPermanente.responsavel.nome}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${bemPermanente.fotos[0] != null}">
                                <img src="/Esposende/documentoDigital/${bemPermanente.fotos[0].id}" class="imagemMiniatura" />
                            </c:when>
                            <c:otherwise>
                                <img src="/Esposende/resources/sem_imagem.gif" class="imagemMiniatura" />
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <t:pager/>
    </fieldset>

    <%-- botao editar --%>
    <c:if test="${empty termoSubRogoModel.dataSubRogo}">
        <form action="${pageContext.request.contextPath}/termoSubRogo/editar" method="GET">
            <input type="hidden" name="termo" value="${termoSubRogoModel.id}"/>
            <input type="submit" value="Editar"/>
        </form>
    </c:if>

    <%-- botao registrar subrogo --%>
    <c:if test="${empty termoSubRogoModel.dataSubRogo}">
        <form action="${pageContext.request.contextPath}/termoSubRogo/registrar" method="GET">
            <input type="hidden" name="termo" value="${termoSubRogoModel.id}"/>
            <input type="submit" value="Registrar SubRogo"/>
        </form>
    </c:if>

    <%-- botao encerrar subrogo --%>
    <c:if test="${ (!empty termoSubRogoModel.dataSubRogo) && (empty termoSubRogoModel.dataEncerramento)}">
        <form action="${pageContext.request.contextPath}/termoSubRogo/encerrar" method="GET">
            <input type="hidden" name="termo" value="${termoSubRogoModel.id}"/>
            <input type="submit" value="Encerrar"/>
        </form>
    </c:if>

</t:interna>