<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<t:interna>
    <jsp:include page="menu.jsp"/>
    <sf:form action="salvar" method="post" modelAttribute="responsavelModel" enctype="multipart/form-data">
        <sf:hidden path="id"/>
        <sf:hidden path="idDocumentoDigital"/>
        <table class="form">
            <tr>
                <td colspan="2"><sf:errors path="*"/></td>
            </tr>
            <tr>
                <td>Nome</td>
                <td><sf:input path="nome"/></td>
                <td><sf:errors path="nome"/></td>
            </tr>
            <tr>
                <td>Matr&iacute;cula</td>
                <td><sf:input path="matricula"/></td>
                <td><sf:errors path="matricula"/></td>
            </tr>
            <tr>
                <td>E-mail</td>
                <td><sf:input path="email"/></td>
                <td><sf:errors path="email"/></td>
            </tr>
            <tr>

                <td>Foto</td>
                <td><input type="file" name="fotoResponsavel"/></td>
                <td><sf:errors path="fotoResponsavel"/></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input type="submit" value="Enviar" id="enviar" /></td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </sf:form>

</t:interna>

