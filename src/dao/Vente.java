package dao;

import java.sql.*;
import java.util.ArrayList;

public class Vente {
    private int id, id_client, id_vendeur;
    private Timestamp venteDate;
    private double totalAmount;


    public Vente(int id_client, int id_vendeur, double totalAmount, Timestamp date) {
        this.id_client = id_client;
        this.id_vendeur = id_vendeur;
        this.totalAmount = totalAmount;
        this.venteDate = date;
    }

    // Constructor
    public Vente(int id, int id_client, int id_vendeur, Timestamp venteDate, double totalAmount) {
        this.id = id;
        this.id_client = id_client;
        this.id_vendeur = id_vendeur;
        this.venteDate = venteDate;
        this.totalAmount = totalAmount;
    }

    public Vente() {}

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdClient() { return id_client; }
    public void setIdClient(int id_client) { this.id_client = id_client; }

    public int getIdVendeur() { return id_vendeur; }
    public void setIdVendeur(int id_vendeur) { this.id_vendeur = id_vendeur; }
    
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
                int id_client = resultSet.getInt("id_client");
                int id_vendeur = resultSet.getInt("id_vendeur");
                Timestamp venteDate = resultSet.getTimestamp("vente_date");
                double totalAmount = resultSet.getDouble("total_amount");
                
                ventes.add(new Vente(id, id_client, id_vendeur, venteDate, totalAmount));
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
            String query = "INSERT INTO vente (id_client, id_vendeur, vente_date, total_amount) VALUES (?, ?, ?, ?) RETURNING id_vente";
            statement = connection.prepareStatement(query);
            
            statement.setInt(1, this.id_client);
            statement.setInt(2, this.id_vendeur);
            statement.setTimestamp(3, this.venteDate);
            statement.setDouble(4, this.totalAmount);
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
            statement.setInt(2, this.id);
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
            String query = "SELECT v.id_vente, v.vente_date, " +
                           "client.firstname || ' ' || client.lastname AS client_name, " +
                           "vendeur.firstname || ' ' || vendeur.lastname AS vendeur_name, " +
                           "r.title AS recipe, c.category_name, vn.is_nature, " +
                           "vd.quantity, vd.unit_price, " +
                           "(vd.quantity * vd.unit_price) AS sub_total, " +
                           "v.total_amount " +
                           "FROM vente v " +
                           "LEFT JOIN boulangerie_user client ON v.id_client = client.id_user " +
                           "LEFT JOIN boulangerie_user vendeur ON v.id_vendeur = vendeur.id_user " +
                           "JOIN vente_details vd ON v.id_vente = vd.id_vente " +
                           "JOIN recipe r ON vd.id_recipe = r.id_recipe " +
                           "JOIN category c ON r.id_category = c.id_category " +
                           "JOIN recipe_nature vn ON r.id_recipe = vn.id_recipe " +
                           "ORDER BY v.id_vente";

            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String[] saleDetails = new String[10];  // Array to store sale details

                // Populate the sale details array
                saleDetails[8] = String.valueOf(resultSet.getInt("id_vente"));       // Sale ID
                saleDetails[0] = resultSet.getString("vente_date");                  // Sale Date
                saleDetails[1] = resultSet.getString("client_name");                 // Client's full name
                saleDetails[2] = resultSet.getString("vendeur_name");                // Vendeur's full name
                saleDetails[3] = resultSet.getString("recipe");                      // Recipe title
                saleDetails[4] = resultSet.getString("category_name");               // Category name
                saleDetails[5] = String.valueOf(resultSet.getBoolean("is_nature"));  // Nature (true/false)
                saleDetails[6] = String.valueOf(resultSet.getInt("quantity"));       // Quantity
                saleDetails[7] = String.valueOf(resultSet.getDouble("unit_price"));  // Unit price
                // saleDetails[8] = String.valueOf(resultSet.getDouble("sub_total"));   // Sub-total (quantity * unit price)
                saleDetails[9] = String.valueOf(resultSet.getDouble("total_amount"));// Total amount for the sale

                // Add the sale details to the list
                salesList.add(saleDetails);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return salesList;
    }
    
    public static ArrayList<String[]> getFilteredSales(boolean isNature, String categoryName) throws Exception {
        ArrayList<String[]> filteredSales = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    
        try {
            connection = DBConnection.getPostgesConnection();
            
            // Préparer la requête pour filtrer les ventes selon la catégorie et la nature
            String query = "SELECT v.id_vente, v.vente_date, u.firstname || ' ' || u.lastname AS user_name, " +
                            "r.title AS recipe, c.category_name, vn.is_nature, vd.quantity, vd.unit_price, " +
                            "(vd.quantity * vd.unit_price) AS sub_total, v.total_amount " +
                            "FROM vente v " +
                            "LEFT JOIN boulangerie_user u ON v.id_client  = u.id_user " +
                            "JOIN vente_details vd ON v.id_vente = vd.id_vente " +
                            "JOIN recipe r ON vd.id_recipe = r.id_recipe " +
                            "JOIN category c ON r.id_category = c.id_category " +
                            "JOIN recipe_nature vn ON r.id_recipe = vn.id_recipe " +
                            "WHERE c.category_name = ? AND vn.is_nature = ?";
    
            // Préparer la requête avec les paramètres de filtrage
            statement = connection.prepareStatement(query);
            statement.setString(1, categoryName);  // Catégorie
            statement.setBoolean(2, isNature);     // Nature (TRUE ou FALSE)
    
            resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                String[] saleDetails = new String[10];  // Tableau pour stocker les détails de la vente
    
                // Remplir le tableau avec les données
                saleDetails[0] = String.valueOf(resultSet.getInt("id_vente"));
                saleDetails[1] = resultSet.getString("vente_date");
                saleDetails[2] = resultSet.getString("user_name");
                saleDetails[3] = resultSet.getString("recipe");
                saleDetails[4] = resultSet.getString("category_name");
                saleDetails[5] = String.valueOf(resultSet.getBoolean("is_nature"));
                saleDetails[6] = String.valueOf(resultSet.getInt("quantity"));
                saleDetails[7] = String.valueOf(resultSet.getDouble("unit_price"));
                saleDetails[8] = String.valueOf(resultSet.getDouble("sub_total"));
                saleDetails[9] = String.valueOf(resultSet.getDouble("total_amount"));
    
                // Ajouter les détails à la liste
                filteredSales.add(saleDetails);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    
        return filteredSales;
    }
    
    public static ArrayList<String[]> getFilteredSales(String categoryName) throws Exception {
        ArrayList<String[]> filteredSales = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    
        try {
            connection = DBConnection.getPostgesConnection();
            
            // Préparer la requête pour filtrer les ventes selon la catégorie et la nature
            String query = "SELECT v.id_vente, v.vente_date, u.firstname || ' ' || u.lastname AS user_name, " +
                            "r.title AS recipe, c.category_name, vn.is_nature, vd.quantity, vd.unit_price, " +
                            "(vd.quantity * vd.unit_price) AS sub_total, v.total_amount " +
                            "FROM vente v " +
                            "LEFT JOIN boulangerie_user u ON v.id_client  = u.id_user " +
                            "JOIN vente_details vd ON v.id_vente = vd.id_vente " +
                            "JOIN recipe r ON vd.id_recipe = r.id_recipe " +
                            "JOIN category c ON r.id_category = c.id_category " +
                            "JOIN recipe_nature vn ON r.id_recipe = vn.id_recipe " +
                            "WHERE c.category_name = ?";
    
            // Préparer la requête avec les paramètres de filtrage
            statement = connection.prepareStatement(query);
            statement.setString(1, categoryName);  
    
            resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                String[] saleDetails = new String[10];  // Tableau pour stocker les détails de la vente
    
                // Remplir le tableau avec les données
                saleDetails[0] = String.valueOf(resultSet.getInt("id_vente"));
                saleDetails[1] = resultSet.getString("vente_date");
                saleDetails[2] = resultSet.getString("user_name");
                saleDetails[3] = resultSet.getString("recipe");
                saleDetails[4] = resultSet.getString("category_name");
                saleDetails[5] = String.valueOf(resultSet.getBoolean("is_nature"));
                saleDetails[6] = String.valueOf(resultSet.getInt("quantity"));
                saleDetails[7] = String.valueOf(resultSet.getDouble("unit_price"));
                saleDetails[8] = String.valueOf(resultSet.getDouble("sub_total"));
                saleDetails[9] = String.valueOf(resultSet.getDouble("total_amount"));
    
                // Ajouter les détails à la liste
                filteredSales.add(saleDetails);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    
        return filteredSales;
    }
    
    public static ArrayList<String[]> getFilteredparfum(int ingredientId, String categoryName) throws Exception {
        ArrayList<String[]> filteredSales = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    
        try {
            connection = DBConnection.getPostgesConnection();
    
            // SQL query to get sales related to recipes containing the specific ingredient and category
            String query = "SELECT v.id_vente, v.vente_date, u.firstname || ' ' || u.lastname AS user_name, " +
                           "r.title AS recipe, c.category_name, vn.is_nature, vd.quantity, vd.unit_price, " +
                           "(vd.quantity * vd.unit_price) AS sub_total, v.total_amount " +
                           "FROM vente v " +
                           "LEFT JOIN boulangerie_user u ON v.id_user = u.id_user " +
                           "JOIN vente_details vd ON v.id_vente = vd.id_vente " +
                           "JOIN recipe r ON vd.id_recipe = r.id_recipe " +
                           "JOIN category c ON r.id_category = c.id_category " +
                           "JOIN recipe_nature vn ON r.id_recipe = vn.id_recipe " +
                           "JOIN recipe_ingredient ri ON r.id_recipe = ri.id_recipe " +
                           "JOIN ingredient i ON ri.id_ingredient = i.id_ingredient " +
                           "WHERE c.category_name = ? AND i.id_ingredient = ?";
    
            // Préparer la requête avec les paramètres de filtrage
            statement = connection.prepareStatement(query);
            statement.setString(1, categoryName);  // Catégorie
            statement.setInt(2, ingredientId);     // ID de l'ingrédient
    
            resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                String[] saleDetails = new String[10];  // Tableau pour stocker les détails de la vente
    
                // Remplir le tableau avec les données
                saleDetails[0] = String.valueOf(resultSet.getInt("id_vente"));
                saleDetails[1] = resultSet.getString("vente_date");
                saleDetails[2] = resultSet.getString("user_name");
                saleDetails[3] = resultSet.getString("recipe");
                saleDetails[4] = resultSet.getString("category_name");
                saleDetails[5] = String.valueOf(resultSet.getBoolean("is_nature"));
                saleDetails[6] = String.valueOf(resultSet.getInt("quantity"));
                saleDetails[7] = String.valueOf(resultSet.getDouble("unit_price"));
                saleDetails[8] = String.valueOf(resultSet.getDouble("sub_total"));
                saleDetails[9] = String.valueOf(resultSet.getDouble("total_amount"));
    
                // Ajouter les détails à la liste
                filteredSales.add(saleDetails);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    
        return filteredSales;
    }

    public static void filteparuser(ArrayList<String[]> ventes, int iduser) throws Exception {
        if (ventes == null) {
            return; // Si la liste est nulle, on ne fait rien
        }
    
        // Récupérer l'utilisateur en fonction de son ID
        User u = User.getById(iduser);
    
        // Construire le nom complet de l'utilisateur
        String name = u.getFirstname() + " " + u.getLastname();
    
        // Itérer sur la liste des ventes et supprimer celles qui ne correspondent pas au nom
        // et celles où le nom du client est null
        ventes.removeIf(sale -> sale[2] == null || !sale[2].equals(name)); // Vérifie si sale[2] est null ou ne correspond pas au nom
    }
    

    public static void filtrepardate(ArrayList<String[]> ventes, String date) throws Exception {
        if (ventes == null || date == null) {
            return; // Si la liste est nulle ou la date est nulle, on ne fait rien
        }
    
        // Itérer sur la liste des ventes et supprimer celles dont la vente_date ne correspond pas à la date
        ventes.removeIf(sale -> {
            // Extraire la partie date de vente_date (format : yyyy-MM-dd)
            String saleDate = sale[1].split(" ")[0]; // Récupère la date avant l'espace (la première partie de la chaîne)
    
            // Comparer la date de la vente avec la date donnée
            return !saleDate.equals(date); // Retourne true pour supprimer la vente
        });
    }
    
    
        
}
