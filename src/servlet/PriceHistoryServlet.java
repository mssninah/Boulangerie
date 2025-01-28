package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.DBConnection;
import dao.PriceHistory;
import dao.Recipe;

public class PriceHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get the price history list
            List<PriceHistory> priceHistories = PriceHistory.getAll(DBConnection.getPostgesConnection());
            req.setAttribute("priceHistories", priceHistories);

            // Get all recipes for the dropdown or filters
            ArrayList<Recipe> recipes = Recipe.all();
            req.setAttribute("recipes", recipes);


            // Additional attributes for active menu and page title
            req.setAttribute("activeMenuItem", "priceHistory");
            req.setAttribute("pageTitle", "Price History List");

            // Forward to the JSP page for displaying the list
            RequestDispatcher dispatcher = req.getRequestDispatcher("price_history_list.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            // Log the error and rethrow as ServletException
            e.printStackTrace();
            throw new ServletException("Error retrieving price history data", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get search parameters from the form
            String recipeId = req.getParameter("recipeId");
            String minDate = req.getParameter("minDate");
            String maxDate = req.getParameter("maxDate");

            // Get all recipes for the dropdown
            ArrayList<Recipe> recipes = Recipe.all();
            req.setAttribute("recipes", recipes);

            // Parse minDate and maxDate to Date objects
            Date minDateParsed = (minDate != null && !minDate.isEmpty()) ? Date.valueOf(minDate) : null;
            Date maxDateParsed = (maxDate != null && !maxDate.isEmpty()) ? Date.valueOf(maxDate) : null;

            // Fetch filtered price histories
            List<PriceHistory> priceHistories = PriceHistory.getFiltered(
                DBConnection.getPostgesConnection(),
                recipeId,
                minDateParsed,
                maxDateParsed
            );

            req.setAttribute("priceHistories", priceHistories);

            // Additional attributes for active menu and page title
            req.setAttribute("activeMenuItem", "priceHistory");
            req.setAttribute("pageTitle", "Price History List");

            // Forward to the JSP page for displaying the filtered list
            RequestDispatcher dispatcher = req.getRequestDispatcher("price_history_list.jsp");
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error filtering price history data", e);
        }
    }
}
