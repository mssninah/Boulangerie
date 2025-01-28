package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;
import java.sql.ResultSet;

public class HistoryPrice {

    private int id;
    private int id_recipe;
    private double new_price;
    private Timestamp change_date;

    public HistoryPrice(int id_recipe, double new_price, Timestamp change_date) {
        this.id_recipe = id_recipe;
        this.new_price = new_price;
        this.change_date = change_date;
    }

    public HistoryPrice(int id, int id_recipe, double new_price, Timestamp change_date) {
        this.id = id;
        this.id_recipe = id_recipe;
        this.new_price = new_price;
        this.change_date = change_date;
    }

    public int getId() {
        return id;
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

    
    public void setChangeDate(String change_date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(change_date, formatter);
        this.change_date = Timestamp.valueOf(localDate.atStartOfDay());
    }

    public Timestamp getChangeDate() {
        return change_date;
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
            statement.setTimestamp(3, change_date);
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

    // Méthode pour récupérer toutes les entrées de history_price
    public static List<HistoryPrice> getAll() throws Exception {
        List<HistoryPrice> historyPrices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM history_price"
            );
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_recipe = resultSet.getInt("id_recipe");
                double new_price = resultSet.getDouble("new_price");
                Timestamp change_date = resultSet.getTimestamp("change_date");
                HistoryPrice historyPrice = new HistoryPrice(id, id_recipe, new_price, change_date);
                historyPrices.add(historyPrice);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return historyPrices;
    }
}