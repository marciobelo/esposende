<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<t:interna>
    <jsp:include page="menu.jsp"/>
    <script type="text/javascript">
        buscaParaBaixa = function () {
            $("#encontrados").load("busca?busca=" + $("#busca").val());
        }

        adicionarBem = function (bem) {
            $.get("adiciona?bem=" + bem, function (data) {
                if (data.length > 0) alert(data);
                $("#encontrados").load("busca?busca=" + $("#busca").val(), function () {
                    $("#selecionados").load("selecionados");
                });
            });
        }

        removeBem = function (bem, baixa) {
            $.get("remove?bem=" + bem + "&baixa=" + baixa, function () {
                $("#selecionados").load("selecionados", function () {
                    buscaParaBaixa();
                });
            });
        }
    </script>

    <sf:form action="termo" method="post" modelAttribute="baixaModel">
        <sf:hidden path="id"/>

        <sf:hidden path="idProtocolo"/>
        <sf:hidden path="seqProtocolo"/>
        <sf:hidden path="anoProtocolo"/>

        <table class="form">
            <tr>
                <td>Protocolo</td>
                <td>${baixaModel.seqProtocolo}/${baixaModel.anoProtocolo}</td>
            </tr>
            <tr>
                <td>Data do Termo</td>
                <td><sf:input class="data" path="dataTermoBaixa"/></td>
            </tr>
            <tr>
                <td>Processo</td>
                <td><sf:input path="numeroProcesso"/></td>
            </tr>
            <tr>
                <td>Data Processo</td>
                <td><sf:input class="data" path="dataProcesso"/></td>
            </tr>
            <tr>
                <td>Justificativa</td>
                <td><sf:textarea rows="4" cols="40" path="justificativa"/></td>
            </tr>
            <tr>
                <td>Data Baixa Contábil</td>
                <td><sf:input class="data" path="dataBaixaContabil"/>${erro}</td>
            </tr>
            <tr>
                <td colspan="2">
                    <t:visualizaMiniaturas documentosDigital="${baixaModel.comprovantes}"
                                           classe="Baixa" id="${baixaModel.id}" editavel="${true}"/>
                </td>
            </tr>
            <c:if test="${baixaModel.dataBaixaContabil == null or erro != null}">
                <tr>
                    <td></td>
                    <td><input type="submit" value="Salvar"/></td>
                </tr>
            </c:if>
        </table>
    </sf:form>

    <c:if test="${baixaModel.dataBaixaContabil != null and erro == null}">
        <table class="tablesorter">
            <thead>
            <th>Descrição</th>
            <th># Tombamento</th>
            <th>Responsável</th>
            <th>&nbsp;</th>
            </thead>
            <tbody>
            <c:forEach items="${baixaModel.bens}" var="bem">
                <tr>
                    <td><c:out value="${bem.descricao}"/></td>
                    <td><c:out value="${bem.tombamento.codTombamento}"/></td>
                    <td><c:out value="${bem.responsavel.nome}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${bem.fotos[0] != null}">
                                <img src="/Esposende/documentoDigital/${bem.fotos[0].id}" class="imagemMiniatura"/>
                            </c:when>
                            <c:otherwise>
                                <img src="/Esposende/resources/sem_imagem.gif" class="imagemMiniatura"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${baixaModel.dataBaixaContabil == null or erro != null}">
        <div id="bens">
            <input type="text" id="busca"/> <input type="button" value="Busca" onclick="buscaParaBaixa()"/>

            <div>
                <div class="divisao">
                    <h3>Bens Encontrados</h3>

                    <div id="encontrados"></div>
                </div>
                <div class="divisao">
                    <h3>Bens Selecionados</h3>

                    <div id="selecionados"></div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            $("#selecionados").load("selecionados");
        </script>
    </c:if>

    <div id="comprovantesBaixa" class="dialog">
        <iframe src="${pageContext.request.contextPath}/documentoDigital/formUpload/BemPermanente/${bem.id}"></iframe>
    </div>
    <script type="text/javascript">
        janela("comprovantesBaixa", null);
    </script>
</t:interna>