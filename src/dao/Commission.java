package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.DBConnection;

public class Commission {
    private int id_commission;
    private int id_vendeur;
    private double value;
    private LocalDate date_debut;
    private LocalDate date_fin;

    // Constructeur
    
    public Commission(int id_commission, int id_vendeur, double value, LocalDate date_debut, LocalDate date_fin) {
        this.id_commission = id_commission;
        this.id_vendeur = id_vendeur;
        this.value = value;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Commission(int id_vendeur, double value, LocalDate date_debut, LocalDate date_fin) {
        this.id_vendeur = id_vendeur;
        this.value = value;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    // Getters et Setters

    public int getId_commission() {
        return id_commission;
    }

    public void setId_commission(int id_commission) {
        this.id_commission = id_commission;
    }

    public int getId_vendeur() {
        return id_vendeur;
    }

    public void setId_vendeur(int id_vendeur) {
        this.id_vendeur = id_vendeur;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    // CREATE
    public void create() throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getPostgesConnection();
            String sql = "INSERT INTO commission (valuee, date_debut, date_fin, id_vendeur) VALUES (?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);

            stmt.setDouble(1, this.getValue());
            stmt.setDate(2, Date.valueOf(this.getDate_debut()));
            stmt.setDate(3, Date.valueOf(this.getDate_fin()));
            stmt.setInt(4, this.getId_vendeur());
            stmt.executeUpdate();
        }finally {
            // Ensure resources are closed
            if (resultSet != null) resultSet.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    // READ
    public List<Commission> all() throws SQLException {
        List<Commission> commissions = new ArrayList<>();

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        String sql = "SELECT * FROM commission";
        try {
            connection = DBConnection.getPostgesConnection();
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                commissions.add(new Commission(
                        rs.getInt("id_commission"),
                        rs.getInt("id_vendeur"),
                        rs.getDouble("valuee"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate()
                ));
            }
        }finally {
            // Ensure resources are closed
            if (resultSet != null) resultSet.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return commissions;
    }

    // UPDATE
    public void update(Commission commission) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        String sql = "UPDATE commission SET valuee = ?, date_debut = ?, date_fin = ?, id_vendeur = ? WHERE id_commission = ?";
        try {
            connection = DBConnection.getPostgesConnection();
            stmt = connection.prepareStatement(sql);

            stmt.setDouble(1, commission.getValue());
            stmt.setDate(2, Date.valueOf(commission.getDate_debut()));
            stmt.setDate(3, Date.valueOf(commission.getDate_fin()));
            stmt.setInt(4, commission.getId_vendeur());
            stmt.setInt(5, commission.getId_commission());
            stmt.executeUpdate();
        }finally {
            // Ensure resources are closed
            if (resultSet != null) resultSet.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        String sql = "DELETE FROM commission WHERE id_commission = ?";
        try {
            connection = DBConnection.getPostgesConnection();
            stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }finally {
            // Ensure resources are closed
            if (resultSet != null) resultSet.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
