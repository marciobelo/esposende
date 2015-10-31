<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.mlens-1.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/visualizacaoImagem.css" />
    <script type="text/javascript">
        $(document).ready(function () {
            $("#fotoAmpliada").mlens({
                imgSrc: $("#fotoAmpliada").attr("src"),
                lensShape: "square",
                lensSize: 200,
                borderSize: 2,
                borderColor: "#000",
                borderRadius: 0,
                margin: '10px'
            });
        });
    </script>
</head>
<body>
<img id="fotoAmpliada" src="${pageContext.request.contextPath}/documentoDigital/${documento}" class="imagemMedia" />
</body>
</html>