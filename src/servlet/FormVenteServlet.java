package servlet;


import dao.Category;
import dao.Recipe;
import dao.Vente;
import dao.VenteDetails;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;


public class FormVenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
                        // Inside your controller, you need to set the recipe list in the request attributes before forwarding the request to the JSP.
            ArrayList<Recipe> recipes = Recipe.all(); // Assuming Recipe.all() returns a list of recipes
            req.setAttribute("recipes", recipes);

            String p = "Formulaire vente";
            req.setAttribute("pageTitle", p);
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
            // Récupérer l'ID de l'utilisateur connecté (si fourni)
            Integer userId = req.getParameter("userId") != null ? Integer.parseInt(req.getParameter("userId")) : 0;
    
            // Initialisation de la vente avec un montant total temporaire de 0
            double totalAmount = 0.0; 
            Vente vente = new Vente(userId, totalAmount);
    
            // Insérer la vente et récupérer l'ID généré
            int venteId = vente.create();
            vente.setId(venteId);
    
            // Récupérer les détails de la vente (recettes, quantités, prix unitaires)
            String[] recipeIds = req.getParameterValues("recipeId");
            String[] quantities = req.getParameterValues("quantity");
            String[] unitPrices = req.getParameterValues("unitPrice");
    
            if (recipeIds != null && quantities != null && unitPrices != null) {
                double calculatedTotalAmount = 0.0;
    
                for (int i = 0; i < recipeIds.length; i++) {
                    // Validation des données récupérées depuis le formulaire
                    int recipeId = Integer.parseInt(recipeIds[i]);
                    int quantity = Integer.parseInt(quantities[i]);
                    double unitPrice = Double.parseDouble(unitPrices[i]);
    
                    // Créer un détail de vente
                    VenteDetails venteDetails = new VenteDetails(venteId, recipeId, quantity, unitPrice);
                    venteDetails.create();
    
                    // Calculer le montant total en fonction des détails
                    calculatedTotalAmount += quantity * unitPrice;
                }
    
                // Mettre à jour le montant total dans la vente
                vente.updateTotalAmount(calculatedTotalAmount);
            }
    
            // Redirection vers la liste des ventes
            resp.sendRedirect("vente?action=list");
    
        } catch (Exception e) {
            throw new ServletException("Une erreur est survenue lors de l'enregistrement de la vente.", e);
        }
    }
    
}
