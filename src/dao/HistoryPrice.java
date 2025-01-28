package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HistoryPrice {
    
    private int id;
    private int id_recipe;
    private double new_price;
    private String change_date;

    public HistoryPrice() {}

    public HistoryPrice(int id) {
        this.id = id;
    }

    public HistoryPrice(int id_recipe, double new_price, String change_date) {
        this.id_recipe = id_recipe;
        this.new_price = new_price;
        this.change_date = change_date;
    }

    public HistoryPrice(int id, int id_recipe, double new_price, String change_date) {
        this.id = id;
        this.id_recipe = id_recipe;
        this.new_price = new_price;
        this.change_date = change_date;
    }

    // Méthode pour récupérer toutes les entrées de HistoryPrice
    public static ArrayList<HistoryPrice> all(Integer id, Integer id_recipe, Double new_price, String change_date) throws Exception {
        ArrayList<HistoryPrice> historyPrices = new ArrayList<HistoryPrice>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getPostgesConnection();
            StringBuilder query = new StringBuilder("SELECT * FROM history_price WHERE 1=1");

            if (id != null) {
                query.append(" AND id = ?");
            }
            if (id_recipe != null) {
                query.append(" AND id_recipe = ?");
            }
            if (new_price != null) {
                query.append(" AND new_price = ?");
            }
            if (change_date != null) {
                query.append(" AND change_date = ?");
            }

            statement = connection.prepareStatement(query.toString());

            int paramIndex = 1;
            if (id != null) {
                statement.setInt(paramIndex++, id);
            }
            if (id_recipe != null) {
                statement.setInt(paramIndex++, id_recipe);
            }
            if (new_price != null) {
                statement.setDouble(paramIndex++, new_price);
            }
            if (change_date != null) {
                statement.setString(paramIndex++, change_date);
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int resultId = resultSet.getInt("id");
                int resultIdRecipe = resultSet.getInt("id_recipe");
                double resultNewPrice = resultSet.getDouble("new_price");
                String resultChangeDate = resultSet.getString("change_date");

                historyPrices.add(new HistoryPrice(resultId, resultIdRecipe, resultNewPrice, resultChangeDate));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return historyPrices;
    }

    // Méthode pour créer une nouvelle entrée dans history_price
    public void create() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO history_price(id_recipe, new_price, change_date)"
                + " VALUES (?, ?, ?)"
            );
            statement.setInt(1, id_recipe);
            statement.setDouble(2, new_price);
            statement.setString(3, change_date);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Méthode pour mettre à jour une entrée dans history_price
    public void update() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "UPDATE history_price"
                + " SET id_recipe = ?, new_price = ?, change_date = ?"
                + " WHERE id = ?"
            );
            statement.setInt(1, id_recipe);
            statement.setDouble(2, new_price);
            statement.setString(3, change_date);
            statement.setInt(4, id);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Méthode pour supprimer une entrée dans history_price
    public void delete() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "DELETE FROM history_price"
                + " WHERE id = ?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRecipe() {
        return id_recipe;
    }

    public void setIdRecipe(int id_recipe) {
        this.id_recipe = id_recipe;
    }

    public double getNewPrice() {
        return new_price;
    }

    public void setNewPrice(double new_price) {
        this.new_price = new_price;
    }

    public String getChangeDate() {
        return change_date;
    }

    public void setChangeDate(String change_date) {
        this.change_date = change_date;
    }

    @Override
    public String toString() {
        return "HistoryPrice [id=" + id + ", id_recipe=" + id_recipe + ", new_price=" + new_price + ", change_date=" + change_date + "]";
    }
}
