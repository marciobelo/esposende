<%@page contentType="text/html" pageEncoding="UTF-8" %>
<ul id="nav" class="naoimprimir">
    <li><a href="${pageContext.request.contextPath}/">Início</a></li>
    <li><a href="${pageContext.request.contextPath}/bempermanente/listar">Bens</a></li>
    <li><a href="${pageContext.request.contextPath}/inventario/responsavel">Inventários</a></li>
    <li><a href="${pageContext.request.contextPath}/baixa/">Baixas</a></li>
    <li><a href="${pageContext.request.contextPath}/termoSubRogo/listar">Subrogos</a></li>
    <li><a href="#">Cadastros</a>
        <ul>
            <li><a href="${pageContext.request.contextPath}/responsavel/listar">Responsável</a></li>
            <li><a href="${pageContext.request.contextPath}/origem/listar">Origem</a></li>
            <li><a href="${pageContext.request.contextPath}/local/list">Local</a></li>
            <li><a href="${pageContext.request.contextPath}/codigocontabil/lista">Códigos Contábeis</a></li>
            <li><a href="${pageContext.request.contextPath}/configuracoes">Configurações</a></li>
        </ul>
    </li>
</ul>