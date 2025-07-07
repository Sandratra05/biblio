<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prêts en cours de ${adherant.nomAdherant}</title>
</head>
<body>
    <h2>Prêts en cours de ${adherant.nomAdherant} ${adherant.prenomAdherant}</h2>

    <c:choose>
        <c:when test="${not empty prets}">
            <table border="1">
                <tr>
                    <th>ID Prêt</th>
                    <th>Date début</th>
                    <th>Exemplaire</th>
                    <th>Livre</th>
                    <th>Type de prêt</th>
                    <th>Action</th>
                </tr>
                <c:forEach items="${prets}" var="pret">
                    <tr>
                        <td>${pret.idPret}</td>
                        <td>${pret.dateDebut}</td>
                        <td>${pret.exemplaire.idExemplaire}</td>
                        <td>${pret.exemplaire.livre.titre}</td>
                        <td>${pret.typePret.type}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/prolongement/demander-prolongement" method="post">
                            <input type="hidden" name="idPret" value="${pret.idPret}" />
                            <input type="hidden" name="idAdherant" value="${adherant.idAdherant}" />
                            <!-- <input type="hidden" name="date" value="<%= java.time.LocalDateTime.now() %>" /> -->
                            <button type="submit">Demander un prolongement</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <p>Aucun prêt en cours avec prolongement trouvé pour cet adhérant.</p>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="${pageContext.request.contextPath}/adherant"> <- Retour</a>
</body>
</html>
