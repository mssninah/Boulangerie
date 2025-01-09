package dao;

import java.sql.*;
import java.util.ArrayList;

public class Vente {
    private int id;
    private int userId;
    private Timestamp venteDate;
    private double totalAmount;
    public Vente(Integer idUser, double totalAmount) {
        this.userId = idUser;  // Peut être null
        this.totalAmount = totalAmount;
    }
    // Constructor
    public Vente(int id, int userId, Timestamp venteDate, double totalAmount) {
        this.id = id;
        this.userId = userId;
        this.venteDate = venteDate;
        this.totalAmount = totalAmount;
    }

    public Vente() {}

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public Timestamp getVenteDate() { return venteDate; }
    public void setVenteDate(Timestamp venteDate) { this.venteDate = venteDate; }
    
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    // Method to fetch all ventes
    public static ArrayList<Vente> all() throws Exception {
        ArrayList<Vente> ventes = new ArrayList<>();
        String query = "SELECT * FROM vente";
        
        try (Connection connection = DBConnection.getPostgesConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id_vente");
                int userId = resultSet.getInt("id_user");
                Timestamp venteDate = resultSet.getTimestamp("vente_date");
                double totalAmount = resultSet.getDouble("total_amount");
                
                ventes.add(new Vente(id, userId, venteDate, totalAmount));
            }
        }
        
        return ventes;
    }

    // Method to create a vente
    public int create() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        try {
            connection = DBConnection.getPostgesConnection();
            String query = "INSERT INTO vente (id_user, total_amount) VALUES (?, ?) RETURNING id_vente";
            statement = connection.prepareStatement(query);

            // Si idUser est nul, on insère NULL dans la base de données
            if (this.userId !=  0 ) {
                statement.setInt(1, this.userId);  // insère l'ID de l'utilisateur
            } else {
                statement.setNull(1, java.sql.Types.INTEGER);  // insère NULL pour l'utilisateur
            }
            
            statement.setDouble(2, this.totalAmount);
            generatedKeys = statement.executeQuery();

            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            }

            return this.id;
        } finally {
            if (generatedKeys != null) generatedKeys.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Method to update a vente
    public void update() throws Exception {
        String query = "UPDATE vente SET id_user = ?, vente_date = ?, total_amount = ? WHERE id_vente = ?";
        
        try (Connection connection = DBConnection.getPostgesConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, userId);
            statement.setTimestamp(2, venteDate);
            statement.setDouble(3, totalAmount);
            statement.setInt(4, id);
            statement.executeUpdate();
        }
    }

    // Method to delete a vente
    public void delete() throws Exception {
        String query = "DELETE FROM vente WHERE id_vente = ?";
        
        try (Connection connection = DBConnection.getPostgesConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void updateTotalAmount(double newTotalAmount) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getPostgesConnection();
            String query = "UPDATE vente SET total_amount = ? WHERE id_vente = ?";
            statement = connection.prepareStatement(query);
            statement.setDouble(1, newTotalAmount);
            statement.setInt(2, this.idVente);
            statement.executeUpdate();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public static ArrayList<String[]> getSalesList() throws Exception {
        ArrayList<String[]> salesList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    
        try {
            connection = DBConnection.getPostgesConnection();
            String query = "SELECT id_vente, vente_date, user_name, id_recipe, recipe_name, qtt, pu, sub_total, total_vente " +
                           "FROM liste_ventes";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                String[] saleDetails = new String[9];
                saleDetails[0] = String.valueOf(resultSet.getInt("id_vente"));
                saleDetails[1] = resultSet.getString("vente_date");
                saleDetails[2] = resultSet.getString("user_name");
                saleDetails[3] = String.valueOf(resultSet.getInt("id_recipe"));
                saleDetails[4] = resultSet.getString("recipe_name");
                saleDetails[5] = String.valueOf(resultSet.getInt("qtt"));
                saleDetails[6] = String.valueOf(resultSet.getDouble("pu"));
                saleDetails[7] = String.valueOf(resultSet.getDouble("sub_total"));
                saleDetails[8] = String.valueOf(resultSet.getDouble("total_vente"));
    
                salesList.add(saleDetails);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    
        return salesList;
    }
    
}
