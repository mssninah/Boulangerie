package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

<<<<<<< Updated upstream
    private static final String postgres_hote = "192.168.1.173";  // Remplacez par l'adresse IP de votre hôte
    private static final String postgres_port = "5433"; // Le port par défaut de PostgreSQL est 5432
=======
    private static final String postgres_hote = "192.168.141.137";
    private static final String postgres_port = "5432"; // Le port par défaut de PostgreSQL est 5432
>>>>>>> Stashed changes
    private static final String postgres_bdd = "boulangerie";
    private static final String postgres_utilisateur = "postgres";
    private static final String postgres_mdp = "postgres";
    // private static String postgres_encoding = "WIN1256";

    // private static final String postgresql_url = "jdbc:postgresql://" + postgres_hote + ":" + postgres_port + "/" + postgres_bdd + "?charSet=" + postgres_encoding;
    private static final String postgresql_url = "jdbc:postgresql://" + postgres_hote + ":" + postgres_port + "/" + postgres_bdd;

    public static final String postgres_driver = "org.postgresql.Driver";

    public static Connection getPostgesConnection() throws ClassNotFoundException, SQLException {
        Connection postgres = null;
        Class.forName(postgres_driver); // Utilisez la classe du pilote PostgreSQL
        postgres = DriverManager.getConnection(postgresql_url, postgres_utilisateur, postgres_mdp);
        return postgres;
    }

}
