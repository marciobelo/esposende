<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:interna>
    <div class="quadro">
        <table class="tablesorter">
            <thead>
            <tr>
                <th colspan="2">Quadro Geral</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Total de Bens</td>
                <td>${totalBens}</td>
            </tr>
            <tr>
                <td>Patrimoniados</td>
                <td>${patrimoniados}</td>
            </tr>
            <tr>
                <td><a href="#" onclick="abrePopup('bempermanente/naopatrimoniados')" class="popup">
                    Patrimônio Interno</a></td>
                <td>${naoPatrimoniados}</td>
            </tr>
            <tr>
                <td><a href="#" onclick="abrePopup('termoSubRogo/benssubrogados')" class="popup">Sub-rogados</a></td>
                <td>${subRogados}</td>
            </tr>
            <tr>
                <td><a href="#" onclick="abrePopup('inventario/naolocalizados')" class="popup">Não Localizados</a></td>
                <td>${naoLocalizados}</td>
            </tr>
            <tr>
                <td><a href="#" onclick="abrePopup('inventario/danificados')" class="popup">Danificados</a></td>
                <td>${danificados}</td>
            </tr>
            <tr>
                <td><a href="#" onclick="abrePopup('inventario/emdesuso')" class="popup">Em Desuso</a></td>
                <td>${emdesuso}</td>
            </tr>
            <tr>
                <td><a href="#" onclick="abrePopup('baixa/embaixa')" class="popup">Em Baixa</a></td>
                <td>${embaixa}</td>
            </tr>
            <tr>
                <td><a href="#" onclick="abrePopup('baixa/baixados')" class="popup">Baixados</a></td>
                <td>${baixados}</td>
            </tr>
            </tbody>
        </table>
        <t:pager/>
    </div>

    <c:if test="${bensNaoLocalizados[0] != null}">
        <div class="quadro">
            <table class="tablesorter">
                <thead>
                <tr>
                    <th colspan="4">Bens Não Localizados</th>
                </tr>
                <tr>
                    <th>Descrição</th>
                    <th>Responsável Interno</th>
                    <th>Inventário</th>
                    <th>Data Abertura</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bensNaoLocalizados}" var="bem">
                    <tr>
                        <td><a target="_blank" href="bempermanente/editar?bemPermanente=${bem[0].id}">${bem[0].descricao}</a></td>
                        <td>${bem[0].responsavel.nome}</td>
                        <td><a target="_blank" href="inventario/${bem[1].id}">${bem[1].protocolo}</a></td>
                        <td><fmt:formatDate value="${bem[1].dataEmissao}" pattern="dd/MM/yyyy"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <t:pager/>
        </div>
    </c:if>

    <c:if test="${inventariosAbertos[0] != null}">
        <div class="quadro">
            <table class="tablesorter">
                <thead>
                <tr>
                    <th colspan="3">Inventários em Aberto</th>
                </tr>
                <tr>
                    <th>Protocolo</th>
                    <th>Responsável Interno</th>
                    <th>Abertura</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${inventariosAbertos}" var="inventario">
                    <tr>
                        <td><a target="_blank" href="inventario/${inventario.id}">${inventario.protocolo}</a></td>
                        <td>${inventario.responsavel.nome}</td>
                        <td><fmt:formatDate value="${inventario.dataEmissao}" pattern="dd/MM/yyyy"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <t:pager/>
        </div>
    </c:if>

    <c:if test="${inventariosAtraso[0] != null}">
        <div class="quadro">
            <table class="tablesorter">
                <thead>
                <tr>
                    <th colspan="2">Inventários em Atraso</th>
                </tr>
                <tr>
                    <th>Descrição</th>
                    <th>Responsável Interno</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${inventariosAtraso}" var="bem">
                    <tr>
                        <td>${bem.descricao}</td>
                        <td>${bem.responsavel.nome}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <t:pager/>
        </div>
    </c:if>

    <c:if test="${semFotos[0] != null}">
        <div class="quadro">
            <table class="tablesorter">
                <thead>
                <tr>
                    <th colspan="4">Bens sem Fotos</th>
                </tr>
                <tr>
                    <th>Descrição</th>
                    <th>Tombamento</th>
                    <th>Responsável Interno</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${semFotos}" var="bem">
                    <tr>
                        <td>${bem.descricao}</td>
                        <td>${bem.tombamento.codTombamento}</td>
                        <td>${bem.responsavel.nome}</td>
                        <td><a href="/Esposende/bempermanente/editar?bemPermanente=${bem.id}">Editar</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <t:pager/>
        </div>
    </c:if>

    <c:if test="${semDocumentoAquisicao[0] != null}">
        <div class="quadro">
            <table class="tablesorter">
                <thead>
                <tr>
                    <th colspan="4">Bens sem Documento de Aquisição</th>
                </tr>
                <tr>
                    <th>Descrição</th>
                    <th>Tombamento</th>
                    <th>Responsável Interno</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${semDocumentoAquisicao}" var="bem">
                    <tr>
                        <td>${bem.descricao}</td>
                        <td>${bem.tombamento.codTombamento}</td>
                        <td>${bem.responsavel.nome}</td>
                        <td><a href="/Esposende/bempermanente/editar?bemPermanente=${bem.id}">Editar</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <t:pager/>
        </div>
    </c:if>
</t:interna>