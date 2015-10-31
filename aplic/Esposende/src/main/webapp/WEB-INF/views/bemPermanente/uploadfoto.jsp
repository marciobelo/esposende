<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:interna>
    <h2>${bem.descricao}</h2>
    <h3>Origem: ${bem.origem.resumo}</h3>
    <h3>Responsável: ${bem.responsavel.nome}</h3>

    <form method="post" enctype="multipart/form-data">
        <input type="hidden" value="${bem.id}" />
        <input type="file" name="foto" />
        <input type="submit" value="Enviar" />
    </form>

    <img src="${pageContext.request.contextPath}/bempermanente/bem/foto/${bem.id}" style="height: 400px;" />
</t:interna>