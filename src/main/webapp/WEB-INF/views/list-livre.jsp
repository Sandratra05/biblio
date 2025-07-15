<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Livres</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .navbar {
            background-color: #333;
            overflow: hidden;
            display: flex;
            padding: 10px 20px;
        }
        .navbar a {
            color: white;
            text-decoration: none;
            padding: 14px 20px;
            display: block;
        }
        .navbar a:hover {
            background-color: #575757;
        }
        .container {
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
    </style>
</head>
<body>

    <!-- Content Section -->
    <div class="container">
        <h1>Liste des Livres Disponibles</h1>

        <!-- Filters Section -->
        <form method="get" action="/filterBooks">
            <label for="date">Date de sortie:</label>
            <input type="date" id="date" name="date">
            
            <label for="auteur">Auteur:</label>
            <input type="text" id="auteur" name="auteur">
            
            <label for="categorie">Categorie:</label>
            <select id="categorie" name="categorie">
                <option value="">Toutes</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat}">${cat}</option>
                </c:forEach>
            </select>
            
            <button type="submit">Filtrer</button>
        </form>

        <table>
            <thead>
                <tr>
                    <th>Titre</th>
                    <th>Auteur</th>
                    <th>ISBN</th>
                    <th>Langue</th>
                    <th>Categorie</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="livre" items="${livres}">
                    <tr>
                        <td>${livre.getTitre()}</td>
                        <td>${livre.getAuteur().getNomAuteur()}</td>
                        <td>${livre.getIsbn()}</td>
                        <td>${livre.getLangue()}</td>
                        <td>
                            <c:forEach var="categorie" items="${livre.categories}">
                                ${categorie.getNomCategorie()}<br>
                            </c:forEach>
                        </td>
                        <td>
                            <input type="hidden" name="adherant">
                            <button onclick="location.href='${pageContext.request.contextPath}/livre/detail?id=${livre.getIdLivre()}'">Detail</button>
                            <!-- <form action="" method="get"></form> -->
                            <form action="${pageContext.request.contextPath}/reservation/reserveBook" method="post">
                                <input type="hidden" name="livre" value="${livre.idLivre}">
                                <input type="hidden" name="adherant">
                                <input type="hidden" name="date" value="<%= java.time.LocalDate.now() %>">
                                <button type="submit">R&eacute;server</button>
                                <!-- <input type="hidden" name="livre" value="${livre.idLivre}">
                                <button type="submit">R&eacute;server</button> -->
                            </form>
                            <!-- <button onclick="location.href='${pageContext.request.contextPath}/reservation/reserveBook?id=${livre.getIdLivre()}'">Reserver</button> -->
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>