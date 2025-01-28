package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommissionSexe {
    private String sexe;
    private double totalCommission;

    // Constructeur
    public CommissionSexe(String sexe, double totalCommission) {
        this.sexe = sexe;
        this.totalCommission = totalCommission;
    }

    // Getters
    public String getSexe() {
        return sexe;
    }

    public double getTotalCommission() {
        return totalCommission;
    }

    // Setters
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setTotalCommission(double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public static List<CommissionSexe> getAll() {
        List<CommissionSexe> commissions = new ArrayList<>();
        String query = "SELECT sexe, total_commission FROM comissions_sexe";

        try (Connection conn = DBConnection.getPostgesConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                commissions.add(new CommissionSexe(
                    rs.getString("sexe"),
                    rs.getDouble("total_commission")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commissions;
    }
}
