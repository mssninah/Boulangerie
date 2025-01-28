package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.HistoryPrice;

public class HistoryPriceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");

            if (action != null && action.equals("delete")) {
                int id = Integer.parseInt(req.getParameter("id"));
                HistoryPrice historyPrice = new HistoryPrice(id);
                historyPrice.delete();
            }

            // Récupérer tous les historiques de prix (peut filtrer selon les critères si nécessaire)
            Integer id = req.getParameter("id") != null ? Integer.parseInt(req.getParameter("id")) : null;
            Integer idRecipe = req.getParameter("idRecipe") != null ? Integer.parseInt(req.getParameter("idRecipe")) : null;
            Double newPrice = req.getParameter("newPrice") != null ? Double.parseDouble(req.getParameter("newPrice")) : null;
            String changeDate = req.getParameter("changeDate");

            // Appel de la méthode all() de HistoryPrice pour récupérer les données
            ArrayList<HistoryPrice> historyPrices = HistoryPrice.all(id, idRecipe, newPrice, changeDate);
            
            // Mettre les données dans la requête pour les afficher dans la vue
            req.setAttribute("historyPrices", historyPrices);
            req.setAttribute("activeMenuItem", "historyPrice");
            req.setAttribute("pageTitle", "Historique des prix");
            
            // Rediriger vers la page JSP qui va afficher les données
            RequestDispatcher dispatcher = req.getRequestDispatcher("historyPrice.jsp");
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    
}
