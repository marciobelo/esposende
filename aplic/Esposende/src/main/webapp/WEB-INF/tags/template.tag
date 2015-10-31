<%@tag description="Template genérico" pageEncoding="UTF-8"%>
<%-- <%@attribute name="header" fragment="true" %> --%>
<%-- <%@attribute name="footer" fragment="true" %> --%>
<html>
<head>
	<title>Esposende</title>
</head>
<body>
	<div id="cabecalho">
		<%-- <jsp:invoke fragment="header"/> --%>
		<p>Esposende</p>
	</div>
	<div id="body">
		<jsp:doBody />
	</div>
	<div id="rodape">
		<%-- <jsp:invoke fragment="footer"/> --%>
		<p>Esposende</p>
	</div>
</body>
</html>