<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.Recipe" %>
<%@ page import="java.util.List" %>

<%@ include file="header.jsp" %>

<div class="container">
    <h2>Insert Price History</h2>
    <form action="form-history" method="post">
        <!-- Recipe dropdown -->
        <div class="form-group">
            <label for="recipe_id">Select Recipe</label>
            <select name="recipe_id" id="recipe_id" class="form-control">
                <option value="">Select Recipe</option>
                <% 
                    List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
                    for (Recipe recipe : recipes) {
                %>
                    <option value="<%= recipe.getId() %>"><%= recipe.getTitle() %></option>
                <% } %>
            </select>
        </div>

        <!-- Price input -->
        <div class="form-group">
            <label for="price">Price</label>
            <input type="number" name="price" id="price" class="form-control" required>
        </div>

        <!-- Start Date input -->
        <div class="form-group">
            <label for="start_date">Start Date</label>
            <input type="date" name="start_date" id="start_date" class="form-control" required>
        </div>

        <!-- End Date input -->
        <div class="form-group">
            <label for="end_date">End Date</label>
            <input type="date" name="end_date" id="end_date" class="form-control" >
        </div>

        <!-- Hidden User ID input -->
        <input type="hidden" name="user_id" value="<%= request.getAttribute("userId") != null ? request.getAttribute("userId") : "" %>">

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<%@ include file="footer.jsp" %>
