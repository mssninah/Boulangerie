package servlet;

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
            int id_client = Integer.parseInt(req.getParameter("clientId")); // Récupérer l'ID du client
            int id_vendeur = Integer.parseInt(req.getParameter("vendeurId")); // Récupérer l'ID du vendeur
    
            // Initialisation de la vente avec un montant total temporaire de 0
            double totalAmount = 0.0; 
            Vente vente = new Vente(id_client, id_vendeur, totalAmount);
    
            // Insérer la vente et récupérer l'ID généré
            int venteId = vente.create();
            vente.setId(venteId);
    
            // Récupérer les détails de la vente (recettes, quantités, prix unitaires)
            String[] recipeIds = req.getParameterValues("recipeId[]");
            String[] quantities = req.getParameterValues("quantity[]");
            String[] unitPrices = req.getParameterValues("unitPrice[]");
            
            // Log pour débogage
            System.out.println("recipeIds: " + Arrays.toString(recipeIds));
            System.out.println("quantities: " + Arrays.toString(quantities));
            System.out.println("unitPrices: " + Arrays.toString(unitPrices));
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
