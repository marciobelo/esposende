<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:interna>

    <sf:form action="encerrar" method="post" modelAttribute="termoSubRogoEncerramentoModel">
        <sf:hidden path="idTermoSubRogo" value="${idTermoSubRogo}" />
        <sf:hidden path="idResponsavel" value="${idResponsavel}" />
        <table>
            <tbody>
                <tr><td colspan="2"><sf:errors path="*" /></td></tr>
                <tr>
                    <td>Nº Protocolo:</td>
                    <td>${termoSubRogoEncerramentoModel.protocolo}</td>
                </tr>
                <tr>
                    <td>Data SubRogo:</td>
                    <td>
                        <fmt:formatDate pattern="dd/MM/yyyy" value="${termoSubRogoEncerramentoModel.dataSubRogo}" />
                    </td>
                </tr>
                <tr>
                    <td>Data Encerramento:</td>
                    <td>
                        <sf:input path="dataEncerramento" class="data" />
                        <sf:errors path="dataEncerramento" />
                    </td>
                </tr>
            </tbody>
        </table>

        <div>
            <span>ATENÇÃO: após encerrado, os bens arrolados neste subrogo continuarão
            sob a responsabilidade interna do subrogado.
            O administrador deve determinar se deve atribuí-los para outro responsável ou não.
            </span>
        </div>

        <input type="submit" value="Registrar" />
    </sf:form>

</t:interna>