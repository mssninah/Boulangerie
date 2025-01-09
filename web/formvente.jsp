<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="dao.Recipe, dao.Category, java.util.ArrayList, util.SessionUtils" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire de Vente</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fc;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding-top: 50px;
        }

        .form-title {
            text-align: center;
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
            color: #5d5d5d;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            background-color: #fff;
        }

        .submit-btn {
            background-color: #7b4bf6;
            color: white;
            font-size: 16px;
            font-weight: bold;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s ease;
        }

        .submit-btn:hover {
            background-color: #5f33e2;
        }

        .table-container {
            margin-top: 20px;
            border-radius: 8px;
            overflow: hidden;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #7b4bf6;
            color: white;
        }

        td input {
            width: 100%;
        }

        .form-row {
            display: flex;
            justify-content: space-between;
        }

        .form-row div {
            flex: 1;
            margin-right: 10px;
        }

        .form-row div:last-child {
            margin-right: 0;
        }

        .icon {
            color: #7b4bf6;
            margin-right: 10px;
        }
    </style>
</head>
<body>


    <div class="container">
        <div class="form-title">
            <h2><i class="fas fa-shopping-cart icon"></i> Créer une Vente</h2>
        </div>
    
        <form action="formvente" method="post">
            <!-- Date -->
            <div class="form-group">
                <label for="date">Date</label>
                <input type="date" id="date" name="date" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" required>
            </div>
    
            <!-- Table for Vente Details -->
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Recette</th>
                            <th>Quantité</th>
                            <th>Prix Unitaire</th>
                            <th>Sous-total</th>
                        </tr>
                    </thead>
                    <tbody id="venteDetailsTable">
                        <tr>
                            <td>

                                // dont have jstl , i list it like thaht,  <% for (Recipe recipe : (ArrayList<Recipe>) request.getAttribute("recipes")) { %>
                                    <tr>
                                        <td><strong><%= recipe.getId() %>
                                        </strong></td>
                                        <td><%= recipe.getTitle() %>
                                        </td>
                                        <td><%= recipe.getDescriptionExcerpt() %>
                                        </td>
                                        <td><%= recipe.getIdCategory() %>
                                        </td>
                                        <td><%= recipe.getHumanFormattedCookTime() %>
                                        </td>
                                        <td><%= recipe.getCreatedBy() %>
                                        </td>
                                        <td><%= recipe.getHumanFormattedCreatedDate() %>
                                        </td>
                                        <td>make it like thaht
                                <select name="recipeId" class="form-control" required>
                                    <c:forEach var="recipe" items="${recipes}">
                                        <option value="${recipe.id}">${recipe.title}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <input type="number" name="quantity" class="form-control" placeholder="Quantité" min="1" required oninput="calculateSubtotal(this)">
                            </td>
                            <td>
                                <input type="number" name="unitPrice" class="form-control" placeholder="Prix Unitaire" step="0.01" required oninput="calculateSubtotal(this)">
                            </td>
                            <td>
                                <input type="number" name="subtotal" class="form-control" placeholder="Sous-total" readonly>
                            </td>
                        </tr>
                    </tbody>
                </table>
    
                <div class="form-group">
                    <button type="button" onclick="addRow()" class="submit-btn">Ajouter une ligne</button>
                </div>
            </div>
    
            <!-- Submit Button -->
            <div class="form-group">
                <button type="submit" class="submit-btn">Créer Vente</button>
            </div>
        </form>
    </div>
    
    <script>
        function calculateSubtotal(input) {
            let row = input.closest('tr');
            let quantity = row.querySelector('input[name="quantity"]').value;
            let unitPrice = row.querySelector('input[name="unitPrice"]').value;
            let subtotalField = row.querySelector('input[name="subtotal"]');
    
            if (quantity && unitPrice) {
                let subtotal = parseFloat(quantity) * parseFloat(unitPrice);
                subtotalField.value = subtotal.toFixed(2);
            } else {
                subtotalField.value = '';
            }
        }
    
        function addRow() {
            let tableBody = document.getElementById('venteDetailsTable');
            let newRow = tableBody.rows[0].cloneNode(true);
            let inputs = newRow.querySelectorAll('input');
            
            inputs.forEach(input => input.value = '');
            
            tableBody.appendChild(newRow);
        }
    </script>
    
    </body>
</html>
