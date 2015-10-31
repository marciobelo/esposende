<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<t:interna>

    <script type="text/javascript">
        function registrar() {
            document.getElementById("formRegistrar").submit();
        }
    </script>

    <sf:form id="formRegistrar" action="registrar" method="post" modelAttribute="termoSubRogoModel">
        <sf:hidden path="id" value="${id}"/>
        <sf:hidden path="idResponsavel" value="${idResponsavel}"/>
        <table class="form">
            <tbody>

            <tr>
                <td>Nº Protocolo:</td>
                <td>${termoSubRogoModel.protocolo}</td>
            </tr>
            <tr>
                <td>Data de Emissão:</td>
                <td>
                    <fmt:formatDate value="${termoSubRogoModel.dataEmissao}" pattern="dd/MM/yyyy"/>
                    <sf:hidden path="dataEmissao" />
                </td>
            </tr>
            <tr>
                <td>Data SubRogo:</td>
                <td>
                    <sf:input path="dataSubRogo" class="data"/>
                    <sf:errors path="dataSubRogo"/>
                </td>
            </tr>
            <tr>
                <td>Data Prevista Encerramento:</td>
                <td>
                    <sf:input path="dataPrevistaEncerramento" class="data"/>
                    <sf:errors path="dataPrevistaEncerramento"/>
                </td>
            </tr>
            </tbody>
        </table>

        <div>
            <span class="popup">Os bens arrolados nesse SubRogo serão transferidos para a responsabilidade do SubRogado.</span>
        </div>
        <sf:errors path="termosAssinados"/>
    </sf:form>

    <div style="margin-top: 50px">
    <%-- Seção de visualização de miniaturas --%>
    <t:visualizaMiniaturas documentosDigital="${termoSubRogoModel.termosAssinados}" classe="TermoSubRogo"
                           id="${termoSubRogoModel.id}" editavel="true"/>
    </div>



    <input type="button" value="Registrar" onclick="registrar();"/>

</t:interna>