package servlet;

import dao.CommissionViewDAO;
import dao.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ComissionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Récupérer l'utilisateur connecté depuis la session
            User user = (User) req.getSession().getAttribute("user");
    
            // Si l'utilisateur n'est pas connecté, rediriger vers la page de login
            if (user == null) {
                req.setAttribute("commissions", null);
                req.setAttribute("pageTitle", "Commissions");
                req.setAttribute("errorMessage", "Vous devez être connecté pour voir vos commissions.");
                RequestDispatcher dispatcher = req.getRequestDispatcher("comission.jsp");
                dispatcher.forward(req, resp);
                return;
            }
    
            // Récupérer l'ID du vendeur (en supposant que l'objet user a une méthode getId)
            int vendeurId = user.getId();
    
            // Récupérer la liste des commissions pour le vendeur connecté
            List<CommissionViewDAO> commissions = CommissionViewDAO.getByVendeur(vendeurId);
    
            // Définir la liste des commissions comme attribut de la requête
            req.setAttribute("commissions", commissions);
    
            // Définir d'autres attributs nécessaires
            req.setAttribute("activeMenuItem", "commission");
            req.setAttribute("pageTitle", "Commissions");
    
            // Forward vers la page commission.jsp
            RequestDispatcher dispatcher = req.getRequestDispatcher("comission.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            // Passer l'exception à la page JSP
            req.setAttribute("errorMessage", "Une erreur est survenue lors de la récupération des commissions.");
            throw new ServletException("Erreur lors de la récupération des commissions", e);
        }
    }
    
}
