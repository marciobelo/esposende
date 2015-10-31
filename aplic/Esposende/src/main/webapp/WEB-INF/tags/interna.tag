<%@ tag language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:geral>
	<jsp:attribute name="cabecalho">
        <div id="logo">
            <img alt="logo" src="${pageContext.request.contextPath}/resources/images/icone1.jpg"/>
            <span class="sistema">Esposende</span>
            <span class="instituicao">FAETERJ-Rio</span>
        </div>

	</jsp:attribute>
	<jsp:attribute name="menu">
        <jsp:include page="../partes/menu.jsp"/>
	</jsp:attribute>
	<jsp:attribute name="rodape">
		Esposende
	</jsp:attribute>

    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:geral>