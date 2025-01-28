<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.HistoryPrice" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("pageTitle") %></title>
    <!-- Inclure ici les styles CSS de Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            padding: 20px;
        }
        h1 {
            margin-bottom: 20px;
        }
        table {
            margin-bottom: 20px;
        }
        form {
            max-width: 600px;
            margin: 0 auto;
        }
        .form-group {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center"><%= request.getAttribute("pageTitle") %></h1>
        
        <table class="table table-bordered table-striped">
            <thead class="thead-dark">
                <tr>
                    <th>#</th>
                    <th>Recette ID</th>
                    <th>Prix</th>
                    <th>Date de changement</th>
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
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    
    <!-- Inclure les scripts JavaScript de Bootstrap -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>