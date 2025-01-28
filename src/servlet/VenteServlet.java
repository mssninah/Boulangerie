package servlet;

import dao.Category;
import dao.Ingredient;
import dao.User;
import dao.Vente;
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
            
                // Récupérer la liste des ventes
                ArrayList<String[]> salesList = Vente.getSalesList();
                req.setAttribute("liste_ventes", salesList);
                req.setAttribute("pageTitle", "Liste des Ventes");

                ArrayList<Ingredient> i= Ingredient.liste_parfum();
                req.setAttribute("parfum", i);
                // Retrieve all categories for the filter dropdown
                ArrayList<Category> categories = Category.all();
                req.setAttribute("categories", categories);

                List<User> users = User.all();
                users = User.getClients(users);
                req.setAttribute("users", users);

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
            String value = req.getParameter("parfum");
            ArrayList<String[]> filteredSales = null;
    
            String user = req.getParameter("user");
            String date = req.getParameter("date_vente");
 
            int p = 0;
            // Filter sales based on the selected parfum or "nature"
            if (value != null && value.equals("nature")) {
                filteredSales = Vente.getFilteredSales(true, categoryName);
                p=1;
            } else if(value!=null && !value.isEmpty()){
                p=1;
                int i = Integer.parseInt(value);
                filteredSales = Vente.getFilteredparfum(i, categoryName);
            }

            // if (value.isEmpty()) {
            //     filteredSales = Vente.getFilteredSales(categoryName);
            //     p=1;
            // }
            

            if (user != null && !user.isEmpty()) {
                if (p==0) {
                    filteredSales= Vente.getSalesList();
                    System.out.println("if 1 :" + filteredSales.size());
                    p=1;
                }
                System.out.println("if 2 :" + filteredSales.size());
                int u = Integer.parseInt(user);  // Effectuer la conversion uniquement si la chaîne n'est pas vide
                Vente.filteparuser(filteredSales, u);
            }

            if (date != null && !date.isEmpty()) {
                if (p==0) {
                    filteredSales= Vente.getSalesList();
                }
                Vente.filtrepardate(filteredSales, date);
            }
            // Pass filtered sales results to the view
            req.setAttribute("filteredSales", filteredSales);
            req.setAttribute("pageTitle", "Ventes Filtrées");
            
            ArrayList<Ingredient> i= Ingredient.liste_parfum();
            req.setAttribute("parfum", i);
            // Retrieve all categories for the filter dropdown
            ArrayList<Category> categories = Category.all();
            req.setAttribute("categories", categories);

            List<User> users = User.all();
            users = User.getClients(users);
            req.setAttribute("users", users);
    
            // Optionally clear the session attribute "filtre"
            req.getSession().removeAttribute("filtre");
    
            // Forward the request to the "vente.jsp" page
            RequestDispatcher dispatcher = req.getRequestDispatcher("vente.jsp");
            dispatcher.forward(req, resp);
    
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }  
}
