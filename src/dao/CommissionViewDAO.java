package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class CommissionViewDAO {

    // Attributs représentant les colonnes de la vue "voir_comissions"
    private int idCommission;
    private int idVendeur;
    private int idVente;
    private BigDecimal totalAmount;
    private BigDecimal pourcentageCommission;
    private java.sql.Timestamp venteDate;
    private BigDecimal montantCommission;

    // Constructeur par défaut
    public CommissionViewDAO() {}

    // Constructeur avec paramètres
    public CommissionViewDAO(int idCommission, int idVendeur, int idVente, BigDecimal totalAmount,
                              BigDecimal pourcentageCommission, java.sql.Timestamp venteDate, BigDecimal montantCommission) {
        this.idCommission = idCommission;
        this.idVendeur = idVendeur;
        this.idVente = idVente;
        this.totalAmount = totalAmount;
        this.pourcentageCommission = pourcentageCommission;
        this.venteDate = venteDate;
        this.montantCommission = montantCommission;
    }

    // Méthode statique pour récupérer toutes les commissions depuis la vue
    public static List<CommissionViewDAO> getAll() throws Exception {
        List<CommissionViewDAO> commissions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Connexion à la base de données
            connection = DBConnection.getPostgesConnection();
            String query = "SELECT * FROM voir_comissions";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            // Parcourir les résultats
            while (resultSet.next()) {
                commissions.add(new CommissionViewDAO(
                        resultSet.getInt("id_commission"),
                        resultSet.getInt("id_vendeur"),
                        resultSet.getInt("id_vente"),
                        resultSet.getBigDecimal("total_amount"),
                        resultSet.getBigDecimal("pourcentage_commission"),
                        resultSet.getTimestamp("vente_date"),
                        resultSet.getBigDecimal("montant_commission")
                ));
            }
        } finally {
            // Fermeture des ressources
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return commissions;
    }

    // Méthode pour rechercher des commissions par vendeur
    public static List<CommissionViewDAO> getByVendeur(int vendeurId) throws Exception {
        List<CommissionViewDAO> commissions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Connexion à la base de données
            connection = DBConnection.getPostgesConnection();
            String query = "SELECT * FROM voir_comissions WHERE id_vendeur = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, vendeurId);
            resultSet = statement.executeQuery();

            // Parcourir les résultats
            while (resultSet.next()) {
                commissions.add(new CommissionViewDAO(
                        resultSet.getInt("id_commission"),
                        resultSet.getInt("id_vendeur"),
                        resultSet.getInt("id_vente"),
                        resultSet.getBigDecimal("total_amount"),
                        resultSet.getBigDecimal("pourcentage_commission"),
                        resultSet.getTimestamp("vente_date"),
                        resultSet.getBigDecimal("montant_commission")
                ));
            }
        } finally {
            // Fermeture des ressources
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return commissions;
    }

    public static List<CommissionViewDAO> getByVendeur(int vendeurId, List<CommissionViewDAO> commissions) {
        List<CommissionViewDAO> filteredCommissions = new ArrayList<>();
        
        for (CommissionViewDAO commission : commissions) {
            if (commission.getIdVendeur() == vendeurId) {
                filteredCommissions.add(commission);
            }
        }
        
        return filteredCommissions;
    }
    
    
    // Méthode statique pour récupérer les commissions entre deux dates
    public static List<CommissionViewDAO> getCommissionDate(Timestamp dateDebut, Timestamp dateFin) throws Exception {
        List<CommissionViewDAO> commissions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Connexion à la base de données
            connection = DBConnection.getPostgesConnection();
            String query = "SELECT * FROM voir_comissions WHERE vente_date BETWEEN ? AND ?";
            statement = connection.prepareStatement(query);
            statement.setTimestamp(1, dateDebut);
            statement.setTimestamp(2, dateFin);
            resultSet = statement.executeQuery();

            // Parcourir les résultats
            while (resultSet.next()) {
                commissions.add(new CommissionViewDAO(
                        resultSet.getInt("id_commission"),
                        resultSet.getInt("id_vendeur"),
                        resultSet.getInt("id_vente"),
                        resultSet.getBigDecimal("total_amount"),
                        resultSet.getBigDecimal("pourcentage_commission"),
                        resultSet.getTimestamp("vente_date"),
                        resultSet.getBigDecimal("montant_commission")
                ));
            }
        } finally {
            // Fermeture des ressources
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return commissions;
    }
    
    
    // Getters et Setters
    public int getIdCommission() {
        return idCommission;
    }

    public void setIdCommission(int idCommission) {
        this.idCommission = idCommission;
    }

    public int getIdVendeur() {
        return idVendeur;
    }

    public void setIdVendeur(int idVendeur) {
        this.idVendeur = idVendeur;
    }

    public int getIdVente() {
        return idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPourcentageCommission() {
        return pourcentageCommission;
    }

    public void setPourcentageCommission(BigDecimal pourcentageCommission) {
        this.pourcentageCommission = pourcentageCommission;
    }

    public java.sql.Timestamp getVenteDate() {
        return venteDate;
    }

    public void setVenteDate(java.sql.Timestamp venteDate) {
        this.venteDate = venteDate;
    }

    public BigDecimal getMontantCommission() {
        return montantCommission;
    }

    public void setMontantCommission(BigDecimal montantCommission) {
        this.montantCommission = montantCommission;
    }

    @Override
    public String toString() {
        return "CommissionViewDAO{" +
                "idCommission=" + idCommission +
                ", idVendeur=" + idVendeur +
                ", idVente=" + idVente +
                ", totalAmount=" + totalAmount +
                ", pourcentageCommission=" + pourcentageCommission +
                ", venteDate=" + venteDate +
                ", montantCommission=" + montantCommission +
                '}';
    }
}
