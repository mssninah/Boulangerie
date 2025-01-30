package dao;

import java.sql.*;
import java.util.ArrayList;

public class VenteDetailsView {
    private int venteId;
    private Date venteDate;
    private double totalAmount;
    private int clientId;
    private String clientName;
    private int vendeurId;
    private String vendeurName;
    private int idRecipe;
    private String recipe;
    private String categoryName;
    private boolean isNature;
    private int quantity;
    private double unitPrice;
    private double subTotal;

    // Constructor
    public VenteDetailsView(int venteId, Date venteDate, double totalAmount, int clientId, String clientName, int vendeurId, String vendeurName, int idRecipe, String recipe, String categoryName, boolean isNature, int quantity, double unitPrice, double subTotal) {
        this.venteId = venteId;
        this.venteDate = venteDate;
        this.totalAmount = totalAmount;
        this.clientId = clientId;
        this.clientName = clientName;
        this.vendeurId = vendeurId;
        this.vendeurName = vendeurName;
        this.idRecipe = idRecipe;
        this.recipe = recipe;
        this.categoryName = categoryName;
        this.isNature = isNature;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
    }

    // Getters and setters
    public int getVenteId() { return venteId; }
    public void setVenteId(int venteId) { this.venteId = venteId; }

    public Date getVenteDate() { return venteDate; }
    public void setVenteDate(Date venteDate) { this.venteDate = venteDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public int getVendeurId() { return vendeurId; }
    public void setVendeurId(int vendeurId) { this.vendeurId = vendeurId; }

    public String getVendeurName() { return vendeurName; }
    public void setVendeurName(String vendeurName) { this.vendeurName = vendeurName; }

    public int getIdRecipe() { return idRecipe; }
    public void setIdRecipe(int idRecipe) { this.idRecipe = idRecipe; }

    public String getRecipe() { return recipe; }
    public void setRecipe(String recipe) { this.recipe = recipe; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public boolean getIsNature() { return isNature; }
    public void setIsNature(boolean isNature) { this.isNature = isNature; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }

    // Method to fetch all vente details view
    public static ArrayList<VenteDetailsView> all() throws Exception {
        ArrayList<VenteDetailsView> venteDetailsViews = new ArrayList<>();
        String query = "SELECT * FROM vente_details_view";
        
        try (Connection connection = DBConnection.getPostgesConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                int venteId = resultSet.getInt("id_vente");
                Date venteDate = resultSet.getDate("vente_date");
                double totalAmount = resultSet.getDouble("total_amount");
                int clientId = resultSet.getInt("client_id");
                String clientName = resultSet.getString("client_name");
                int vendeurId = resultSet.getInt("id_vendeur");
                String vendeurName = resultSet.getString("vendeur_name");
                
                int idRecipe = resultSet.getInt("id_recipe");
                System.out.println("id_recipe: " + idRecipe);
                
                String recipe = resultSet.getString("recipe");
                String categoryName = resultSet.getString("category_name");
                boolean isNature = resultSet.getBoolean("is_nature");
                int quantity = resultSet.getInt("quantity");
                double unitPrice = resultSet.getDouble("unit_price");
                double subTotal = resultSet.getDouble("sub_total");
                
                venteDetailsViews.add(new VenteDetailsView(venteId, venteDate, totalAmount, clientId, clientName, vendeurId, vendeurName, idRecipe, recipe, categoryName, isNature, quantity, unitPrice, subTotal));
            }
        }
        return venteDetailsViews;
    }

    public static ArrayList<VenteDetailsView> getFilteredNature(boolean isNature, ArrayList<VenteDetailsView> venteDetailsViews) throws Exception {
        ArrayList<VenteDetailsView> filteredVenteDetailsViews = new ArrayList<>();
        
        for (VenteDetailsView venteDetailsView : venteDetailsViews) {
            if (venteDetailsView.getIsNature() == isNature) {
                filteredVenteDetailsViews.add(venteDetailsView);
            }
        }
        return filteredVenteDetailsViews;
    }

    public static ArrayList<VenteDetailsView> getFilteredCategory(String categoryName, ArrayList<VenteDetailsView> venteDetailsViews) throws Exception {
        ArrayList<VenteDetailsView> filteredVenteDetailsViews = new ArrayList<>();
        
        for (VenteDetailsView venteDetailsView : venteDetailsViews) {
            if (venteDetailsView.getCategoryName().equals(categoryName)) {
                filteredVenteDetailsViews.add(venteDetailsView);
            }
        }
        return filteredVenteDetailsViews;
    }

    public static ArrayList<VenteDetailsView> getFilteredClient(int clientId, ArrayList<VenteDetailsView> venteDetailsViews) {
        ArrayList<VenteDetailsView> filteredVenteDetailsViews = new ArrayList<>();
        
        for (VenteDetailsView venteDetailsView : venteDetailsViews) {
            if (venteDetailsView.getClientId() == clientId) {
                filteredVenteDetailsViews.add(venteDetailsView);
            }
        }
        return filteredVenteDetailsViews;
    }

    public static ArrayList<VenteDetailsView> getFilteredDate(Date date, ArrayList<VenteDetailsView> venteDetailsViews) {
        ArrayList<VenteDetailsView> filteredVenteDetailsViews = new ArrayList<>();
        
        for (VenteDetailsView venteDetailsView : venteDetailsViews) {
            if (venteDetailsView.getVenteDate().equals(date)) {
                filteredVenteDetailsViews.add(venteDetailsView);
            }
        }
        return filteredVenteDetailsViews;
    }

    public static ArrayList<VenteDetailsView> getFilteredParfum(int parfum, ArrayList<VenteDetailsView> all)throws Exception {
        ArrayList<VenteDetailsView> filteredVenteDetailsViews = new ArrayList<>();
        
        for (VenteDetailsView venteDetailsView : all) {
            int idRecipe = venteDetailsView.getIdRecipe();
            System.out.println(idRecipe);
            ArrayList<RecipeIngredient> recipeIngredients = RecipeIngredient.getByIdRecipe(idRecipe);
            boolean found = RecipeIngredient.checkIngredient(parfum, recipeIngredients);
            System.out.println("miditra");
            System.out.println(found);
            if (found) {
                filteredVenteDetailsViews.add(venteDetailsView);
            }
        }
        return filteredVenteDetailsViews;
    }

}