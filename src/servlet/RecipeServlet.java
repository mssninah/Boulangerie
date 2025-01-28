package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.Category;
import dao.HistoryPrice;
import dao.Recipe;
import dao.Ingredient;
public class RecipeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");

            // Handle recipe deletion
            if (action != null && action.equals("deldete")) {
                int id = Integer.parseInt(req.getParameter("id"));
                Recipe recipe = new Recipe(id);
                recipe.delete();
            }

            // Retrieve all categories
            ArrayList<Category> categories = Category.all();

            // Extract search parameters
            String title = req.getParameter("searchTitle") == null ? "" : req.getParameter("searchTitle");
            String description = req.getParameter("searchDescription") == null ? "" : req.getParameter("searchDescription");
            int idCategory = req.getParameter("searchIdCategory") == null ? 0 : Integer.parseInt(req.getParameter("searchIdCategory"));
            String minCookTimeStr = req.getParameter("searchMinCookTime");
            String maxCookTimeStr = req.getParameter("searchMaxCookTime");
            LocalTime minCookTime = minCookTimeStr != null && !minCookTimeStr.isEmpty() ? LocalTime.parse(minCookTimeStr) : null;
            LocalTime maxCookTime = maxCookTimeStr != null && !maxCookTimeStr.isEmpty() ? LocalTime.parse(maxCookTimeStr) : null;
            String creator = req.getParameter("searchCreator") == null ? "" : req.getParameter("searchCreator");
            String minCreationDateStr = req.getParameter("searchMinCreationDate");
            String maxCreationDateStr = req.getParameter("searchMaxCreationDate");
            LocalDate minCreationDate = minCreationDateStr != null && !minCreationDateStr.isEmpty() ? LocalDate.parse(minCreationDateStr) : null;
            LocalDate maxCreationDate = maxCreationDateStr != null && !maxCreationDateStr.isEmpty() ? LocalDate.parse(maxCreationDateStr) : null;
            int searchIngredientId = req.getParameter("searchIngredient") == null ? 0 : Integer.parseInt(req.getParameter("searchIngredient"));

            // Search recipes based on filters
            ArrayList<Recipe> recipes = Recipe.search(title, description, idCategory, minCookTime, maxCookTime, creator, minCreationDate, maxCreationDate, searchIngredientId);

            // Set attributes for the JSP
            req.setAttribute("recipes", recipes);
            req.setAttribute("categories", categories);
            req.setAttribute("activeMenuItem", "recipe");
            req.setAttribute("pageTitle", "Recette");

            // Forward request to the JSP
            RequestDispatcher dispatcher = req.getRequestDispatcher("recipe.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    try {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("idRecipe"));
        String title = req.getParameter("recipeTitle");
        String description = req.getParameter("recipeDescription");
        int idCategory = Integer.parseInt(req.getParameter("recipeIdCategory"));
        LocalTime cookTime = LocalTime.parse(req.getParameter("recipeCookTime"));
        String createdBy = req.getParameter("recipeCreator");

        String dateString = req.getParameter("recipeCreationDate");
        LocalDate creationDate = LocalDate.parse(dateString);
        Timestamp timestamp = null;
            if (dateString != null && !dateString.isEmpty()) {
                if (dateString.length() == 10) { // Format "yyyy-MM-dd"
                    dateString += " 00:00:00"; // Ajouter les heures, minutes et secondes
                }
    
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = dateFormat.parse(dateString);
                timestamp = new Timestamp(parsedDate.getTime());
            }

        
        double prix = Double.parseDouble(req.getParameter("recipePrice"));

        // Log parameters
        System.out.println("Action: " + action);
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Category ID: " + idCategory);
        System.out.println("Cook Time: " + cookTime);
        System.out.println("Created By: " + createdBy);
        System.out.println("Created Date: " + timestamp);
        System.out.println("Price: " + prix);

        Recipe recipe = new Recipe(id, title, description, idCategory, cookTime, createdBy, creationDate, prix);
        HistoryPrice histo = new HistoryPrice(id,prix,timestamp);

        // Handle update or create actions
        if (action != null && action.equals("update")) {
            recipe.update();
            histo.create();
        } else {
            recipe.create();
        }

        resp.sendRedirect("recipe");
    } catch (Exception e) {
        throw new ServletException(e);
    }
}
}
