import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDeReunion {

    private int id;
    private String nom;
    private int capacite;
    private boolean disponible;
    private double tarif;

    public SalleDeReunion() {}

    public SalleDeReunion(int id, String nom, int capacite, boolean disponible, double tarif) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
        this.disponible = disponible;
        this.tarif = tarif;
    }

    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public double getTarif() { return tarif; }
    public void setTarif(double tarif) { this.tarif = tarif; }

    // Ajouter
    public void ajouterSalle() {
        Connection conn = ConnexionDB.getConnection();
        String sql = "INSERT INTO salle_de_reunion (nom, capacite, disponible, tarif) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nom);
            ps.setInt(2, capacite);
            ps.setBoolean(3, disponible);
            ps.setDouble(4, tarif);
            ps.executeUpdate();

            System.out.println("Salle ajoutée !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Modifier
    public void modifierSalle(int id) {
        Connection conn = ConnexionDB.getConnection();
        String sql = "UPDATE salle_de_reunion SET nom=?, capacite=?, disponible=?, tarif=? WHERE id=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nom);
            ps.setInt(2, capacite);
            ps.setBoolean(3, disponible);
            ps.setDouble(4, tarif);
            ps.setInt(5, id);
            ps.executeUpdate();

            System.out.println("Salle modifiée !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer
    public void supprimerSalle(int id) {
        Connection conn = ConnexionDB.getConnection();
        String sql = "DELETE FROM salle_de_reunion WHERE id=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Salle supprimée !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Vérifier disponibilité
    public boolean verifierDisponibilite(int id) {
        Connection conn = ConnexionDB.getConnection();
        String sql = "SELECT disponible FROM salle_de_reunion WHERE id=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("disponible");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Liste des salles
    public static List<SalleDeReunion> getToutesLesSalles() {
        List<SalleDeReunion> salles = new ArrayList<>();
        Connection conn = ConnexionDB.getConnection();

        String sql = "SELECT * FROM salle_de_reunion";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                SalleDeReunion salle = new SalleDeReunion(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("capacite"),
                        rs.getBoolean("disponible"),
                        rs.getDouble("tarif")
                );
                salles.add(salle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salles;
    }
}