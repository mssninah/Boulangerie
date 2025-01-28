package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import dao.DBConnection;
import dao.PriceHistory;
import dao.Recipe;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FormPriceHistory extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get all recipes for the dropdown
            List<Recipe> recipes = Recipe.all();  // Fetch all recipes
            req.setAttribute("recipes", recipes);

            // Forward to the JSP page to display the form
            RequestDispatcher dispatcher = req.getRequestDispatcher("form_price_history.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching data for price history form.");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get data from the form with validation for empty fields
            String recipeIdStr = req.getParameter("recipe_id");
            String priceStr = req.getParameter("price");
            String userIdStr = req.getParameter("user_id");
    
            // Handle empty fields
            int recipeId = 0;
            double price = 0.0;
            int userId = 0;
    
            if (recipeIdStr != null && !recipeIdStr.isEmpty()) {
                recipeId = Integer.parseInt(recipeIdStr); // Only parse if not empty
            }
    
            if (priceStr != null && !priceStr.isEmpty()) {
                price = Double.parseDouble(priceStr); // Only parse if not empty
            }
    
            if (userIdStr != null && !userIdStr.isEmpty()) {
                userId = Integer.parseInt(userIdStr); // Only parse if not empty
            }
    
            // Check if start_date and end_date are provided; if not, set them to null
            java.sql.Date startDate = null;
            java.sql.Date endDate = null;
    
            String startDateString = req.getParameter("start_date");
            String endDateString = req.getParameter("end_date");
    
            if (startDateString != null && !startDateString.isEmpty()) {
                startDate = java.sql.Date.valueOf(startDateString); // Convert to java.sql.Date if provided
            }
    
            if (endDateString != null && !endDateString.isEmpty()) {
                endDate = java.sql.Date.valueOf(endDateString); // Convert to java.sql.Date if provided
            }
    
            // Insert into price_history table (handling null dates)
            PriceHistory.insertPriceHistory(recipeId, price, startDate, endDate, userId);
    
            // Redirect to the price history list page after insertion
            resp.sendRedirect("history");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    
}
