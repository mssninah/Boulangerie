package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    // Pool de connexions
    private static HikariDataSource dataSource;

    // Le chemin relatif vers le fichier de configuration dans le classpath
    private static final String CONFIG_PATH = "hikari.properties";

    // Bloc statique pour initialiser HikariCP à partir du fichier de configuration
    static {
        try {
            // Chargement des propriétés depuis le fichier
            Properties properties = new Properties();
            
            // Charger le fichier hikari.properties depuis le classpath
            try (InputStream fis = DBConnection.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {
                if (fis == null) {
                    throw new RuntimeException("Le fichier de configuration hikari.properties est introuvable dans le classpath.");
                }
                properties.load(fis);
            }

            // Configuration de HikariCP
            HikariConfig config = new HikariConfig(properties);
            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier de configuration HikariCP : " + e.getMessage());
            throw new RuntimeException("Impossible de charger le fichier de configuration HikariCP", e);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de HikariCP : " + e.getMessage());
            throw new RuntimeException("Impossible d'initialiser HikariCP", e);
        }
    }

    // Méthode pour obtenir une connexion depuis le pool
    public static Connection getPostgesConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Méthode pour fermer le pool (à appeler lors de l'arrêt de l'application)
    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
