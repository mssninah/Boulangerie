package servlet;

import dao.Category;
import dao.Vente;
import dao.VenteDetails;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class VenteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");

            if ("list".equals(action)) {
                // Récupérer la liste des ventes
                ArrayList<String[]> salesList = Vente.getSalesList();
                req.setAttribute("liste_ventes", salesList);
                req.setAttribute("pageTitle", "Liste des Ventes");
                // Retrieve all categories for the filter dropdown
                ArrayList<Category> categories = Category.all();
                req.setAttribute("categories", categories);
                RequestDispatcher dispatcher = req.getRequestDispatcher("vente.jsp");
                dispatcher.forward(req, resp);
            }

 
            
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // String action = req.getParameter("action");

        // try {
        //     if ("create".equals(action)) {
        //         // Récupérer l'ID de l'utilisateur. Si l'utilisateur est nul, on passe null.
        //         Integer userId = req.getParameter("userId") != null ? Integer.parseInt(req.getParameter("userId")) : 0;
        //         double totalAmount = 0;  // initialement à 0
        //         Vente vente = new Vente(userId, totalAmount);

        //         int venteId = vente.create(); 
        //         vente.setId(venteId);

        //         // Insérer les détails de la vente
        //         String[] recipeIds = req.getParameterValues("recipeId");
        //         String[] quantities = req.getParameterValues("quantity");
        //         String[] unitPrices = req.getParameterValues("unitPrice");

        //         double calculatedTotalAmount = 0;  // Calculer le total dans le servlet

        //         for (int i = 0; i < recipeIds.length; i++) {
        //             int recipeId = Integer.parseInt(recipeIds[i]);
        //             int quantity = Integer.parseInt(quantities[i]);
        //             double unitPrice = Double.parseDouble(unitPrices[i]);

        //             VenteDetails venteDetails = new VenteDetails(venteId, recipeId, quantity, unitPrice);
        //             venteDetails.create();  // Insérer un détail de vente

        //             // Calculer le total en fonction des détails
        //             calculatedTotalAmount += quantity * unitPrice;
        //         }

        //         // Mettre à jour le montant total dans la table `vente`
        //         vente.updateTotalAmount(calculatedTotalAmount);

        //         resp.sendRedirect("vente?action=list");  // Rediriger vers la liste des ventes
        //     }
        // } catch (Exception e) {
        //     throw new ServletException(e);
        // }     
        try{    
        String categoryName = req.getParameter("categoryName");
        boolean isNature = Boolean.parseBoolean(req.getParameter("isNature"));

        // Récupérer les ventes filtrées
        ArrayList<String[]> filteredSales = Vente.getFilteredSales(isNature, categoryName);

        // Passer les résultats à la vue
        req.setAttribute("filteredSales", filteredSales);
        req.setAttribute("pageTitle", "Ventes Filtrées");
        ArrayList<Category> categories = Category.all();
        req.setAttribute("categories", categories);
        // Optionally clear the session attribute "filtre" if needed
        req.getSession().removeAttribute("filtre");

        RequestDispatcher dispatcher = req.getRequestDispatcher("vente.jsp");
        dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
