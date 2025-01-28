package servlet;

import dao.Commission;
import dao.Recipe;
import dao.Vente;
import dao.VenteDetails;
import dao.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class FormVenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
                        // Inside your controller, you need to set the recipe list in the request attributes before forwarding the request to the JSP.
            ArrayList<Recipe> recipes = Recipe.all(); // Assuming Recipe.all() returns a list of recipes
            
            List<User> users = User.all();
            List<User> clients = User.getClients(users);
            List<User> vendeurs = User.getVendeurs(users);

            String p = "Formulaire vente";
            
            req.setAttribute("recipes", recipes);
            req.setAttribute("pageTitle", p);
            req.setAttribute("clients", clients);
            req.setAttribute("vendeurs", vendeurs);
            // Afficher le formulaire de saisie de vente
            RequestDispatcher dispatcher = req.getRequestDispatcher("form-vente.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id_client = Integer.parseInt(req.getParameter("clientId")); 
            int id_vendeur = Integer.parseInt(req.getParameter("vendeurId"));
    
            String dateString = req.getParameter("date");
    
            Timestamp timestamp = null;
            if (dateString != null && !dateString.isEmpty()) {
                if (dateString.length() == 10) { // Format "yyyy-MM-dd"
                    dateString += " 00:00:00"; // Ajouter les heures, minutes et secondes
                }
    
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = dateFormat.parse(dateString);
                timestamp = new Timestamp(parsedDate.getTime());
            }
    
            // Récupération des détails de la vente
            String[] recipeIds = req.getParameterValues("recipeId[]");
            String[] quantities = req.getParameterValues("quantity[]");
            String[] unitPrices = req.getParameterValues("unitPrice[]");
    
            double totalAmount = 0.0; 
            Vente vente = new Vente(id_client, id_vendeur, totalAmount, timestamp);
    
            int venteId = vente.create();
            vente.setId(venteId);
    
            if (recipeIds != null && quantities != null && unitPrices != null) {
                double calculatedTotalAmount = 0.0;
    
                for (int i = 0; i < recipeIds.length; i++) {
                    int recipeId = Integer.parseInt(recipeIds[i]);
                    int quantity = Integer.parseInt(quantities[i]);
                    double unitPrice = Double.parseDouble(unitPrices[i]);
    
                    VenteDetails venteDetails = new VenteDetails(venteId, recipeId, quantity, unitPrice);
                    venteDetails.create();
    
                    calculatedTotalAmount += quantity * unitPrice;
                }
    
                vente.updateTotalAmount(calculatedTotalAmount);
            }
    
            resp.sendRedirect("vente?action=list");
    
        } catch (Exception e) {
            throw new ServletException("Une erreur est survenue lors de l'enregistrement de la vente.", e);
        }
    }    
    
}
