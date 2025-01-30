package servlet;

import dao.Category;
import dao.Ingredient;
import dao.User;
import dao.VenteDetailsView;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VenteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
                ArrayList<VenteDetailsView> salesList = VenteDetailsView.all();

                ArrayList<Ingredient> parfum= Ingredient.liste_parfum();
                
                ArrayList<Category> categories = Category.all();
                
                List<User> clients = User.all();
                clients = User.getClients(clients);

                req.setAttribute("liste_ventes", salesList);
                req.setAttribute("parfum", parfum);
                req.setAttribute("categories", categories);
                req.setAttribute("users", clients);

                req.setAttribute("pageTitle", "Liste des Ventes");
                RequestDispatcher dispatcher = req.getRequestDispatcher("vente.jsp");
                dispatcher.forward(req, resp);
        
            
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String categoryName = req.getParameter("categoryName");
            int value = Integer.parseInt(req.getParameter("parfum"));
            System.out.println(value);
            int user = Integer.parseInt(req.getParameter("user"));
            String dateStr = req.getParameter("date_vente");
            
            ArrayList<VenteDetailsView> filteredSales = VenteDetailsView.all();
            ArrayList<Ingredient> i= Ingredient.liste_parfum();
            List<User> users = User.all();
            users = User.getClients(users);
            ArrayList<Category> categories = Category.all();
 
            if (categoryName != null && !categoryName.isEmpty()) {
                filteredSales = VenteDetailsView.getFilteredCategory(categoryName, filteredSales);
            }

            if (value == -1) {
                filteredSales = VenteDetailsView.getFilteredNature(true, filteredSales);
            } else if (value != 0) {
                filteredSales = VenteDetailsView.getFilteredParfum(value, filteredSales);
            }

            if (user != 0) {
                filteredSales = VenteDetailsView.getFilteredClient(user, filteredSales);
            }

            if (dateStr != null && !dateStr.isEmpty()) {
                java.sql.Date date = java.sql.Date.valueOf(dateStr);
                filteredSales = VenteDetailsView.getFilteredDate(date, filteredSales);
                
            }

            req.setAttribute("liste_ventes", filteredSales);
            req.setAttribute("pageTitle", "Ventes Filtrées");
            req.setAttribute("parfum", i);
            req.setAttribute("categories", categories);
            req.setAttribute("users", users);
    
            RequestDispatcher dispatcher = req.getRequestDispatcher("vente.jsp");
            dispatcher.forward(req, resp);
    
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
