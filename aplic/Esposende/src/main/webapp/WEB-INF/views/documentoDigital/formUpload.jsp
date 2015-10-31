<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
    <head>
    </head>
    <body>

    <form action="${pageContext.request.contextPath}/documentoDigital/upload" method="post"
        enctype="multipart/form-data">
        <input type="hidden" name="ClasseDestino" value="${documentoDigitalModel.classeDestino}" />
        <input type="hidden" name="id" value="${documentoDigitalModel.id}" />
        <input type="file" name="documento"/>
        <br/>
        <input type="submit" value="Enviar" />
    </form>
    </body>
</html>