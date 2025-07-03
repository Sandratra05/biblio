<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Prêter un Livre</h1>
    <form action="preter" method="post">
        <label for="adherantId">ID de l'adh&eacute;rant :</label>
        <input list="adherant-list" id="adherantId" name="adherantId" required autocomplete="off">
        <datalist id="adherant-list">
            <c:forEach var="ad" items="${adherants}">
                <option value="${ad.idAdherant}">${ad.nomAdherant} ${ad.prenomAdherant}</option>
            </c:forEach>
        </datalist>
        <br><br>
        <select name="typePret" id="typePret">
            <c:forEach var="type" items="${typesPret}">
                <option value="${type.idTypePret}">${type.type}</option>
            </c:forEach>
        </select>
        <br><br>
        <label for="livre">S&eacute;lectionnez le livre à preter :</label>
        <select id="livre" name="livre" required>
            <c:forEach var="livre" items="${livres}">
                <option value="${livre.idLivre}">
                    Livre: ${livre.titre}
                </option>
            </c:forEach>
        </select>
        <br><br>
        <label for="dateFin">Date de fin du pret</label>
        <input type="date" name="dateFin" id="dateFin" required>
        <button type="submit">Valider le pret</button>
    </form>
    <c:if test="${not empty message}">
    <div style="color: red; font-weight: bold;">
        ${message}
    </div>
</c:if>
</body>
</html>