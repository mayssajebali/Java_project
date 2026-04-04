import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {

    // URL de connexion (MySQL par défaut)
    private static final String URL = "jdbc:mysql://localhost:3306/coworking";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // si tu as mis un mot de passe, remplace ""

    // Objet unique de connexion
    private static Connection conn = null;

    // Méthode pour obtenir la connexion
    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Charger le driver MySQL (optionnel depuis Java 8)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Créer la connexion
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion à la base réussie");

            } catch (ClassNotFoundException e) {
                System.out.println("Driver MySQL introuvable : " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Erreur connexion : " + e.getMessage());
            }
        }
        return conn;
    }

    // Méthode pour fermer la connexion (optionnel)
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connexion fermée");
            } catch (SQLException e) {
                System.out.println("Erreur fermeture connexion : " + e.getMessage());
            }
        }
    }
}