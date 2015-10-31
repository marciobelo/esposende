<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:interna>
    <jsp:include page="menu.jsp"/>
    <sf:form method="post" modelAttribute="bem" onsubmit="ajustaCamposBemPermanente(this);"
             enctype="multipart/form-data" style="float:left;" id="formBem">

        <fieldset class="dados">
            <legend>Dados do Bem</legend>
            <sf:hidden path="id"/>
            <table class="form">
                <tr>
                    <td>Origem</td>
                    <td><sf:select path="origem" style="width:67.5%;">
                        <sf:options items="${bem.origens}" itemValue="id" itemLabel="resumo"/>
                    </sf:select>
                        <a href="#" onclick="abreJanela('novaOrigem');">Nova</a>
                    </td>
                    <td><span class="legenda">Responsável</span></td>
                    <td><sf:select path="responsavel" style="width:67.5%;">
                        <sf:options items="${bem.responsaveis}" itemValue="id" itemLabel="nome"/>
                    </sf:select>
                        <a href="#" onclick="abreJanela('novoResponsavel');">Novo</a>
                    </td>
                </tr>
                <tr>
                    <td>Descrição</td>
                    <td colspan="3"><sf:textarea rows="2" cols="40" path="descricao" style="width:100%;"/></td>
                </tr>
                <tr>
                    <td>Informações Adicionais</td>
                    <td colspan="3"><sf:textarea rows="2" cols="40" path="informacaoAdicional" style="width:100%;"/></td>
                </tr>

                <tr>
                    <td>Local de Permanência</td>
                    <td>
                        <sf:select path="localPermanencia">
                            <sf:options items="${bem.locaisPermanencia}" itemValue="id" itemLabel="nome"/>
                        </sf:select>
                    </td>
                    <td><input type="submit" id="enviaForm" value="Enviar"/></td>
                    <td><sf:errors path="*"/></td>
                </tr>
            </table>

            <!-- Seção de visualização de miniaturas -->
            <c:if test="${bem.id != null}">
                <t:visualizaMiniaturas documentosDigital="${bem.fotosBemPermanente}"
                                       classe="BemPermanente" id="${bem.id}" editavel="${true}"/>
            </c:if>
        </fieldset>


        <fieldset class="tombamento">
            <legend>Tombamento</legend>
            <table class="form">
                <tr>
                    <td>Nº Tombamento</td>
                    <td><sf:input path="codigoTombamento"/>
                        <sf:hidden path="idTombamento"/>
                    </td>
                    <td><sf:errors path="codigoTombamento"/></td>
                </tr>
                <tr>
                    <td>Data Tombamento</td>
                    <td><sf:input path="dataTombamento" class="data"/></td>
                    <td><sf:errors path="dataTombamento"/></td>
                </tr>
                <tr>
                    <td>Código Contábil</td>
                    <td>
                        <sf:select path="codigoContabil">
                            <sf:options items="${bem.codigosContabeis}"
                                    itemValue="id" itemLabel="descricaoCompleta" />
                        </sf:select>
                    </td>
                    <td><sf:errors path="codigoContabil"/></td>
                </tr>
                <tr>
                    <td>Tipo de Operação</td>
                    <td>
                        <sf:select path="tipoOperacao">
                            <sf:options items="${bem.operacoes}"/>
                        </sf:select>
                    </td>
                    <td><sf:errors path="tipoOperacao"/></td>
                </tr>
                <tr>
                    <td>Valor da Operação</td>
                    <td><sf:input path="valorOperacao"/></td>
                    <td><sf:errors path="valorOperacao"/></td>
                </tr>
                <tr>
                    <td>Documento Hábil</td>
                    <td><sf:input path="documentoHabil"/></td>
                    <td><sf:errors path="documentoHabil"/></td>
                </tr>
                <tr>
                    <td>Histórico da Operação</td>
                    <td><sf:textarea rows="4" cols="40" path="historicoOperacao"/></td>
                    <td><sf:errors path="historicoOperacao"/></td>
                </tr>
                <tr>
                    <td colspan="3">
                        <c:if test="${bem.id != null}">
                            <t:visualizaMiniaturas documentosDigital="${bem.comprovantes}"
                                                   classe="Tombamento" id="${bem.id}" editavel="${true}"/>
                        </c:if>
                    </td>
                </tr>
            </table>
        </fieldset>

        <fieldset class="historico">
            <legend>Histórico do Bem</legend>
            <table class="tablesorter">
                <thead>
                <tr>
                    <th>Data</th>
                    <th>Tipo</th>
                    <th>Descrição</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bem.historico}" var="historico">
                    <tr>
                        <td><fmt:formatDate value="${historico.data}" pattern="dd/MM/yyyy"/></td>
                        <td>${historico.tipoRegistroOcorrencia.descricao}</td>
                        <td>${historico.descricao}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <t:pager></t:pager>
        </fieldset>
        </fieldset>

    </sf:form>

    <div id="novoResponsavel" class="dialog" title="Novo Responsável">
        <iframe src="${pageContext.request.contextPath}/responsavel/editar" width="500" height="300"></iframe>
    </div>

    <div id="novaOrigem" class="dialog">
        <iframe src="${pageContext.request.contextPath}/origem/novo" width="400" height="300"></iframe>
    </div>

    <c:if test="${bem.id != null}">
        <div id="novaFotoBemPermanente" class="dialog">
            <iframe src="${pageContext.request.contextPath}/documentoDigital/formUpload/BemPermanente/${bem.id}"></iframe>
        </div>
    </c:if>

    <script type="text/javascript">
        janela("novoResponsavel", atualizaResponsaveis);
        janela("novaOrigem", atualizaOrigens);
        janela("novaFotoBemPermanente", null);
    </script>

</t:interna>