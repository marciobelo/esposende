<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:interna>

    <script>
        function adicionarBem(id) {
            var form = document.getElementById("termoSubRogoModel");
            form.idBemAdicionar.value = id;
            form.action = "adicionarBem";
            form.submit();
        }

        function pesquisarBens() {
            trechoDescricao = document.getElementById("descricaoBem").value;
            $("#resultadoPesquisaBens").
                    load("${pageContext.request.contextPath}/termoSubRogo/listarBens?trechoDescricao=" + trechoDescricao);
        }
    </script>

    <sf:form action="salvar" method="post" modelAttribute="termoSubRogoModel">
        <sf:hidden path="id"/>
        <sf:hidden path="idNumeroProtocolar"/>
        <sf:hidden path="idBemAdicionar"/>
        <table class="form">
            <tr>
                <td>Protocolo</td>
                <td>${termoSubRogoModel.protocolo}</td>
            </tr>
            <tr>
                <td>Responsável</td>
                <td>
                    <sf:select path="idResponsavel" style="width:67.5%;">
                        <sf:options items="${termoSubRogoModel.responsaveis}" itemValue="id" itemLabel="nome"/>
                    </sf:select>
                    <a href="#" onclick="abreJanela('novoResponsavel');">Novo</a>
                </td>
            </tr>

            <tr>
                <td>Data Emiss&atilde;o</td>
                <td><sf:input path="dataEmissao" class="data"/></td>
                <td><sf:errors path="dataEmissao"/></td>
            </tr>

            <tr>
                <td>Data Prevista Encerramento:</td>
                <td>
                    <sf:input path="dataPrevistaEncerramento" class="data"/>
                    <sf:errors path="dataPrevistaEncerramento"/>
                </td>
            </tr>

            <tr>
                <td>Prop&oacute;sito</td>
                <td><sf:textarea rows="4" cols="40" path="proposito"/></td>
                <td><sf:errors path="proposito"/></td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <td colspan="2">&nbsp;</td>
            </tr>
        </table>

        <div class="pesquisa">
            <h4>Pesquisa
                    <%-- Seção de Pesquisa de Bem Permanente --%>
                <input type="text" id="descricaoBem" name="descricaoBem"/>
                <input type="button" value="pesquisar" onclick="pesquisarBens();"/>
            </h4>

            <div id="resultadoPesquisaBens"></div>
        </div>

        <div class="pesquisa">
            <h4 style="margin-top: 11px;">Bens Selecionados</h4>
            <!-- tabela dos bens selecionados -->
            <table class="tablesorter">
                <thead>

                <tr>
                    <th>&nbsp;</th>
                    <th>Descrição</th>
                    <th># Tombamento</th>
                    <th>Responsável Atual</th>
                    <th>Foto</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${termoSubRogoModel.idBensSelecionados}" var="idBemSelecionado">
                    <c:set var="bemPermanente" value="${termoSubRogoModel.bens[idBemSelecionado]}"/>
                    <tr>
                        <td><sf:checkbox path="idBensSelecionados" value="${idBemSelecionado}"/></td>
                        <td><c:out value="${bemPermanente.descricao}"/></td>
                        <td><c:out value="${idBemSelecionado}"/></td>
                        <td>${termoSubRogoModel.bens[idBemSelecionado].responsavel.nome}</td>
                        <td>
                            <c:choose>
                                <c:when test="${bemPermanente.fotos[0] != null}">
                                    <img src="/Esposende/documentoDigital/${bemPermanente.fotos[0].id}"
                                         class="imagemMiniatura"/>
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
            <t:pager></t:pager>
        </div>
        <div class="secao">
            <hr class="secao"/>
            <input type="button" onclick="document.getElementById('termoSubRogoModel').submit();" value="Salvar"/>
            <input type="button" onclick="window.history.back();" value="Cancelar"/>
        </div>
    </sf:form>

</t:interna>