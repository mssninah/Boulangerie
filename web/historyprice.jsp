<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.HistoryPrice" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("pageTitle") %></title>
    <!-- Inclure ici les styles CSS de Bootstrap ou autre -->
</head>
<body>
    <h1><%= request.getAttribute("pageTitle") %></h1>
    
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>#</th>
                <th>Recette ID</th>
                <th>Prix</th>
                <th>Date de changement</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                ArrayList<HistoryPrice> historyPrices = (ArrayList<HistoryPrice>) request.getAttribute("historyPrices");
                for (HistoryPrice historyPrice : historyPrices) {
            %>
            <tr>
                <td><%= historyPrice.getId() %></td>
                <td><%= historyPrice.getIdRecipe() %></td>
                <td><%= historyPrice.getNewPrice() %></td>
                <td><%= historyPrice.getChangeDate() %></td>
                <td>
                    <a href="historyPrice?action=delete&id=<%= historyPrice.getId() %>">Supprimer</a>
                    <!-- Ajouter d'autres actions comme modifier si nécessaire -->
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
    
    <!-- Ajouter un formulaire pour créer ou mettre à jour des prix -->
    <form action="historyPrice" method="post">
        <input type="hidden" name="action" value="create"> <!-- ou "update" pour la mise à jour -->
        <!-- Ajouter les champs pour id, idRecipe, newPrice et changeDate -->
        <input type="submit" value="Ajouter/Mettre à jour un prix">
    </form>
</body>
</html>
