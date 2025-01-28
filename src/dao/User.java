package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    private int id_sexe;
    private int id_status;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(int id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(int id, String firstname, String lastname, String email, String password, int id_sexe, int id_status) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.id_sexe = id_sexe;
        this.id_status = id_status;
    }

    public static ArrayList<User> all() throws Exception {
        ArrayList<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getPostgesConnection(); PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM boulangerie_user"
        ); ResultSet resultSet = statement.executeQuery()) {

            int id;
            String firstname;
            String lastname;
            String email;
            String password;
            int id_sexe;
            int id_status;

            while (resultSet.next()) {
                id = resultSet.getInt("id_user");
                firstname = resultSet.getString("firstname");
                lastname = resultSet.getString("lastname");
                email = resultSet.getString("email");
                password = resultSet.getString("user_password");
                id_sexe = resultSet.getInt("id_sexe");
                id_status = resultSet.getInt("id_status");

                users.add(
                        new User(id, firstname, lastname, email, password, id_sexe, id_status)
                );
            }
        }

        return users;
    }

    public void create() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getPostgesConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO boulangerie_user(firstname, lastname, email, user_password)"
                + " VALUES (?, ?, ?, ?)"
            );
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.execute();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public void findByEmailAndPassword() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM boulangerie_user"
                + " WHERE email = ? AND user_password = ?"
            );
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt("id_user");
                firstname = resultSet.getString("firstname");
                lastname = resultSet.getString("lastname");
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
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdStatus(){
        return id_status;
    }

    public void setIdStatus(int id_status){
        this.id_status = id_status;
    }

    public int getIdSexe(){
        return id_sexe;
    }

    public void setIdSexe(int id_sexe){
        this.id_sexe = id_sexe;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
                + ", password=" + password + "]";
    }
    public static User getById(int id) throws Exception {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    
        try {
            connection = DBConnection.getPostgesConnection();
            statement = connection.prepareStatement(
                "SELECT * FROM boulangerie_user WHERE id_user = ?"
            );
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String password = resultSet.getString("user_password");
                user = new User(id, firstname, lastname, email, password);
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
    
        return user;
    }

    public static List<User> getClients(List<User> users) {
        List<User> clients = new ArrayList<>();
        for (User user : users) {
            if (user.getIdStatus() == 2) { //Client = 2;
                clients.add(user);
            }
        }
        return clients;
    }

    public static List<User> getVendeurs(List<User> users) {
        List<User> clients = new ArrayList<>();
        for (User user : users) {
            if (user.getIdStatus() == 3) { //Vendeur = 3;
                clients.add(user);
            }
        }
        return clients;
    }


    
}