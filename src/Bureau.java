import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Bureau {

    private int id;
    private String nom;
    private boolean disponible;
    private double tarif;

    public Bureau(String nom, boolean disponible, double tarif) {
        this.nom = nom;
        this.disponible = disponible;
        this.tarif = tarif;
    }

    public String getNom() {
        return nom;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public double getTarif() {
        return tarif;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    //Ajouter
    public void ajouterBureau() {
        Connection conn = ConnexionDB.getConnection();

        String sql = "INSERT INTO bureau (nom, disponible, tarif) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, getNom());
            ps.setBoolean(2, isDisponible());
            ps.setDouble(3, getTarif());

            ps.executeUpdate();
            System.out.println("Bureau ajouté : " + getNom());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Modifier
    public void modifierBureau(int id) {
        Connection conn = ConnexionDB.getConnection();

        String sql = "UPDATE bureau SET nom = ?, disponible = ?, tarif = ? WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, getNom());
            ps.setBoolean(2, isDisponible());
            ps.setDouble(3, getTarif());
            ps.setInt(4, id);

            int lignesModifiees = ps.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Bureau modifié : " + getNom());
            } else {
                System.out.println("Aucun bureau trouvé avec l'id " + id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Supprimer
    public void supprimerBureau(int id) {
        Connection conn = ConnexionDB.getConnection();

        String sql = "DELETE FROM bureau WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int lignesSupprimees = ps.executeUpdate();
            if (lignesSupprimees > 0) {
                System.out.println("Bureau supprimé avec l'id " + id);
            } else {
                System.out.println("Aucun bureau trouvé avec l'id " + id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Vérifier disponibilité
    public void verifierDisponibilite(int id) {
        Connection conn = ConnexionDB.getConnection();

        String sql = "SELECT disponible FROM bureau WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                boolean dispo = rs.getBoolean("disponible");

                if (dispo) {
                    System.out.println("Bureau disponible");
                } else {
                    System.out.println("Bureau non disponible");
                }
            } else {
                System.out.println("Aucun bureau trouvé avec l'id " + id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}