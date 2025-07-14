<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Validation des réservations</title>
</head>
<body>
<h2>Liste des r&eacute;servations en attente</h2>

<c:if test="${not empty success}">
    <p style="color: green;">${success}</p>
</c:if>

<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Adh&eacute;rant</th>
            <th>Livre</th>
            <th>Date</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${reservationsEnAttente}" var="rs">
        <tr>
            <td>${rs.reservation.idReservation}</td>
            <td>${rs.reservation.adherant.nomAdherant} ${rs.reservation.adherant.prenomAdherant}</td>
            <td>${rs.reservation.livre.titre}</td>
            <td>${rs.reservation.dateDeReservation}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/reservation/valider">
                    <input type="hidden" name="idReservation" value="${rs.reservation.idReservation}" />
                    <button type="submit">Valider</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
