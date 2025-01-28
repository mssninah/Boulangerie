package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PriceHistory {
    private int id;
    private Recipe recipe;
    private double price;
    private Date startDate;
    private Date endDate;
    private User user;

    // Connection à la base de données
    private Connection connection;

    public PriceHistory(Connection connection) {
        this.connection = connection;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Méthode d'insertion
    public void insert() throws Exception {
        String sql = "INSERT INTO price_history (recipe_id, price, start_date, end_date, user_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, recipe.getId());
            stmt.setDouble(2, price);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);
            stmt.setInt(5, user.getId());
            stmt.executeUpdate();
        }
    }

    
   // Method to insert a new price history record into the database
   public static void insertPriceHistory(int recipeId, double price, java.sql.Date startDate, java.sql.Date endDate, int userId) throws Exception {
    // Define the SQL INSERT query
    String sql = "INSERT INTO price_history (recipe_id, price, start_date, end_date, user_id) VALUES (?, ?, ?, ?, ?)";

    // Establish a connection to the database
    try (Connection connection = DBConnection.getPostgesConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {

        // Set the parameters for the PreparedStatement
        stmt.setInt(1, recipeId);      // recipe_id
        stmt.setDouble(2, price);      // price
        stmt.setDate(3, startDate);    // start_date
        stmt.setDate(4, endDate);      // end_date
        stmt.setInt(5, userId);        // user_id

        // Execute the query
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error inserting price history", e);
    }
}
    public PriceHistory(){}
    
    public static List<PriceHistory> getAll(Connection connection) throws Exception {
        String sql = "SELECT * FROM price_history";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            List<PriceHistory> priceHistories = new ArrayList<>();
            
            while (rs.next()) {
                PriceHistory priceHistory = new PriceHistory();
                
                // Remplir les attributs de l'objet PriceHistory à partir du ResultSet
                priceHistory.setId(rs.getInt("id_price"));
                priceHistory.setPrice(rs.getDouble("price"));
                priceHistory.setStartDate(rs.getDate("starts_date"));
                priceHistory.setEndDate(rs.getDate("end_date"));
                
                // Vérifier si id_recipe est non nul et récupérer le Recipe correspondant
                int idRecipe = rs.getInt("id_recipe");
                if (!rs.wasNull()) { // Si id_recipe n'est pas null
                    Recipe recipe = Recipe.getById(idRecipe);
                    priceHistory.setRecipe(recipe); // Set the Recipe object in PriceHistory
                }
                
                // Vérifier si id_user est non nul et récupérer le User correspondant
                int idUser = rs.getInt("id_user");
                if (!rs.wasNull()) { // Si id_user n'est pas null
                    User user = User.getById(idUser);
                    priceHistory.setUser(user); // Set the User object in PriceHistory
                }
                
                // Ajouter l'objet PriceHistory à la liste
                priceHistories.add(priceHistory);
            }
            
            return priceHistories;
        }
    }
    
    

   

    public static List<PriceHistory> getFiltered(Connection connection, String recipeId, Date minDate, Date maxDate) throws Exception {
        // Start with the base SQL query
        String sql = "SELECT * FROM price_history WHERE 1=1";
    
        // Dynamically add filters to the SQL query based on the provided parameters
        if (recipeId != null && !recipeId.isEmpty()) {
            sql += " AND id_recipe = ?";
        }
        if (minDate != null) {
            sql += " AND starts_date >= ?";
        }
        if (maxDate != null) {
            sql += " AND end_date <= ?";
        }
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set the parameters for the query
            int index = 1;
            
            if (recipeId != null && !recipeId.isEmpty()) {
                int recipe= Integer.parseInt(recipeId);
                stmt.setInt(index++, recipe);
            }
            if (minDate != null) {
                stmt.setDate(index++, minDate);
            }
            if (maxDate != null) {
                stmt.setDate(index++, maxDate);
            }
    
            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                List<PriceHistory> priceHistories = new ArrayList<>();
                
                // Process the ResultSet and populate the PriceHistory objects
                while (rs.next()) {
                    PriceHistory priceHistory = new PriceHistory();
                    
                    // Set the attributes of PriceHistory from the ResultSet
                    priceHistory.setId(rs.getInt("id_price"));
                    priceHistory.setPrice(rs.getDouble("price"));
                    priceHistory.setStartDate(rs.getDate("starts_date"));
                    priceHistory.setEndDate(rs.getDate("end_date"));
                    
                    // Retrieve and set the associated Recipe, if applicable
                    int idRecipe = rs.getInt("id_recipe");
                    if (!rs.wasNull()) {
                        Recipe recipe = Recipe.getById(idRecipe); // Assuming getById is a method in Recipe class
                        priceHistory.setRecipe(recipe);
                    }
    
                    // Retrieve and set the associated User, if applicable
                    int idUser = rs.getInt("id_user");
                    if (!rs.wasNull()) {
                        User user = User.getById(idUser); // Assuming getById is a method in User class
                        priceHistory.setUser(user);
                    }
                    
                    // Add the populated PriceHistory object to the list
                    priceHistories.add(priceHistory);
                }
                
                return priceHistories; // Return the list of PriceHistory objects
            }
        }
    }
    
}
