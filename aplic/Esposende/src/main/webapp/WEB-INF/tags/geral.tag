<%@ tag description="página mais externa" pageEncoding="UTF-8" %>
<%@attribute name="cabecalho" fragment="true" %>
<%@attribute name="rodape" fragment="true" %>
<%@attribute name="menu" fragment="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Esposende</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/base.css" type="text/css"
          media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/jquery-ui.css" type="text/css"
          media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/ui.theme.css" type="text/css"
          media="screen"/>
    <link rel="stylesheet" media="screen" type="text/css" href="${pageContext.request.contextPath}/resources/style/menu.css"/>
    <link rel="stylesheet" media="screen" type="text/css" href="${pageContext.request.contextPath}/resources/style/style.css"/>
    <link rel="stylesheet" media="screen" type="text/css"
          href="${pageContext.request.contextPath}/resources/style/jquery.tablesorter.pager.css"/>
    <link rel="stylesheet" media="screen" type="text/css"
          href="${pageContext.request.contextPath}/resources/style/tablesorter.blue.css"/>
    <link rel="stylesheet" media="screen" type="text/css"
          href="${pageContext.request.contextPath}/resources/style/smoothDivScroll.css"/>
    <link rel="stylesheet" media="print" type="text/css"
          href="${pageContext.request.contextPath}/resources/style/impressao.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/funcoes.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/scripts/jquery.tablesorter.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/scripts/jquery.tablesorter.pager.js"></script>

    <%-- dependências do http://www.smoothdivscroll.com --%>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/scripts/jquery.mousewheel.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/scripts/jquery.kinetic.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/scripts/jquery.smoothdivscroll-1.3-min.js"></script>
<script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/scripts/jquery.mlens-1.0.min.js"></script>

    <script>
        $(document).ready(function () {
            $(".tabs").tabs({
                ajaxOptions: {
                    error: function (xhr, status, index, anchor) {
                        $(anchor.hash).html("Erro ao carregar conteúdo");
                    }
                }
            });
            $(".accordion").accordion({active: null});
            $(".data").datepicker();
            $(".tablesorter").tablesorter({widthFixed: true, widgets: ['zebra']}).tablesorterPager({container: $("#pager")});
        });
    </script>
</head>
<body>

<div id="topo" class="secaopagina" class="naoimprimir">
    <jsp:invoke fragment="cabecalho"/>
</div>

<div id="menu" class="secaopagina" class="naoimprimir">
    <jsp:invoke fragment="menu"/>
</div>

<div id="corpo" class="secaopagina">
    <jsp:doBody/>
</div>

<div id="rodape" class="secaopagina" class="naoimprimir">
    <jsp:invoke fragment="rodape"/>
</div>

<script type="text/javascript">
    ajustaAlturaPagina();
    marcaPaginaAtual();
</script>

</body>
</html>