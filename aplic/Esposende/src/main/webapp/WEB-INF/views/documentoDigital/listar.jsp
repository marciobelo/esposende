<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${documentosDigital}" var="documentoDigital">

    <div class="miniatura">
        <a onclick="$('#fotoAmpliada').attr('src', '${pageContext.request.contextPath}/documentoDigital/visualizar/${documentoDigital.id}'); abreJanela('imagemAmpliada');"
           href="#">
            <img src="${pageContext.request.contextPath}/documentoDigital/${documentoDigital.id}"
                 class="imagemMiniatura"/>
        </a>
        <br/>
        <input type="radio" class="delete" title="Remove Anexo" name="${classe}Anexo" value="${documentoDigital.id}"/>
    </div>
</c:forEach>
