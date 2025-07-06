<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Retour d’un Pr&ecirc;t</title>
    <style>
        body {
            font-family: Arial;
            background-color: #f4f4f4;
        }
        .container {
            margin: 100px auto;
            width: 40%;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
        }
        .form-control {
            width: 100%;
            padding: 8px;
            margin-top: 10px;
        }
        .btn {
            margin-top: 15px;
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            width: 100%;
            cursor: pointer;
        }
        .success {
            margin-top: 15px;
            padding: 10px;
            background-color: #d4edda;
            color: #155724;
        }
        .error {
            margin-top: 15px;
            padding: 10px;
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Retour d’un Pr&ecirc;t</h2>

        <c:if test="${not empty success}">
            <div class="success">${success}</div>
        </c:if>

        <c:if test="${not empty message}">
            <div class="error">${message}</div>
        </c:if>

        <form action="retour" method="post">
            <label for="idPret">S&eacute;lectionnez un pr&ecirc;t :</label>
            <select name="idPret" class="form-control" required>
                <c:forEach var="pret" items="${prets}">
                    <option value="${pret.idPret}">
                        Pr&ecirc;t numero ${pret.idPret} - Livre : ${pret.exemplaire.livre.titre}
                        - Adh&eacute;rant : ${pret.adherant.nomAdherant} ${pret.adherant.prenomAdherant}
                    </option>
                </c:forEach>
            </select>
        <label for="dateRetour">Date de retour</label>
            <input type="date" name="dateRetour" id="dateRetour">

            <button type="submit" class="btn">Valider le retour</button>
        </form>
    </div>
</body>
</html>
