package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.HistoryPrice;
import java.util.*;

public class HistoryPriceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Appel de la méthode all() de HistoryPrice pour récupérer les données
            List<HistoryPrice> historyPrices = HistoryPrice.getAll();
            
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
