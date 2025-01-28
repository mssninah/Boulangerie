package servlet;

import dao.CommissionSexe;
import dao.CommissionViewDAO;
import dao.User;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

public class ComissionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users = User.all();
            List<User> vendeurs = User.getVendeurs(users);

            List<CommissionSexe> commissionsSexe = CommissionSexe.getAll();
    
            // Récupérer la liste des commissions pour le vendeur connecté
            List<CommissionViewDAO> commissions = CommissionViewDAO.getAll();
    
            // Définir la liste des commissions comme attribut de la requête
            req.setAttribute("commissions", commissions);
            req.setAttribute("commissionsSexe", commissionsSexe);
            req.setAttribute("vendeurs", vendeurs);
    
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users = User.all();
            List<User> vendeurs = User.getVendeurs(users);
    
            String startDateParam = req.getParameter("startDate");
            String endDateParam = req.getParameter("endDate");
            String vendeurParam = req.getParameter("vendeur");
    
            Timestamp dateDebut = null;
            Timestamp dateFin = null;
            int idVendeur = 0;
    
            if (startDateParam != null && !startDateParam.isEmpty()) {
                dateDebut = Timestamp.valueOf(LocalDate.parse(startDateParam).atStartOfDay());
            }
    
            if (endDateParam != null && !endDateParam.isEmpty()) {
                dateFin = Timestamp.valueOf(LocalDate.parse(endDateParam).atStartOfDay());
            }
    
            if (vendeurParam != null && !vendeurParam.isEmpty()) {
                idVendeur = Integer.parseInt(vendeurParam);
            }
    
            List<CommissionViewDAO> commissions;
    
            if (dateDebut == null || dateFin == null) {
                commissions = CommissionViewDAO.getAll();
            } else {
                commissions = CommissionViewDAO.getCommissionDate(dateDebut, dateFin);
            }
    
            if (idVendeur > 0) {
                commissions = CommissionViewDAO.getByVendeur(idVendeur, commissions);
            }
    
            req.setAttribute("vendeurs", vendeurs);
            req.setAttribute("commissions", commissions);
            req.setAttribute("activeMenuItem", "commission");
            req.setAttribute("pageTitle", "Commissions");
    
            RequestDispatcher dispatcher = req.getRequestDispatcher("comission.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    

    
}
