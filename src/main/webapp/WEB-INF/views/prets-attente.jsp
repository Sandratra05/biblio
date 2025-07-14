<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pr&ecirc;ts en prolongement en attente</title>
</head>
<body>
    <h2>Liste des pr&ecirc;ts avec prolongement en attente</h2>

    <c:choose>
        <c:when test="${not empty prets}">
            <table border="1">
                <thead>
                    <tr>
                        <th>ID Pr&ecirc;t</th>
                        <th>Adh&eacute;rant</th>
                        <th>Date de d&eacute;but</th>
                        <th>Exemplaire</th>
                        <th>Livre</th>
                        <th>Type de pr&ecirc;t</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pret" items="${prets}">
                        <tr>
                            <td>${pret.idPret}</td>
                            <td>${pret.adherant.nomAdherant} ${pret.adherant.prenomAdherant}</td>
                            <td>${pret.dateDebut}</td>
                            <td>${pret.exemplaire.idExemplaire}</td>
                            <td>${pret.exemplaire.livre.titre}</td>
                            <td>${pret.typePret.type}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/prolongement/valider?id=${pret.idPret}">Valider</a> |
                                <a href="${pageContext.request.contextPath}/prolongement/refuser?id=${pret.idPret}">Refuser</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>Aucun pr&ecirc;t avec prolongement en attente trouv&eacute;.</p>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="${pageContext.request.contextPath}/dashboard">← Retour</a>
</body>
</html>
