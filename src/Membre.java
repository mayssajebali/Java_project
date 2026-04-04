import java.sql.Connection;
import java.sql.PreparedStatement;

public class Membre extends Utilisateur{
    private String typeAbonnement;

    public Membre(String nom,String prenom,String email,String mot_de_passe,String typeAbonnement){
        super(nom,prenom,email,mot_de_passe);
        this.typeAbonnement=typeAbonnement;
    }

    public String getTypeAbonnement() {
        return typeAbonnement;
    }

    public void ajouterMembre() {
        Connection conn = ConnexionDB.getConnection();

        String sql = "INSERT INTO membre (nom, prenom,email, motDePasse, typeAbonnement) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, getNom());
            ps.setString(2, getPrenom());
            ps.setString(3, getEmail());
            ps.setString(4, getMotDePasse());
            ps.setString(5, getTypeAbonnement());
            ps.executeUpdate();
            System.out.println("Membre ajouté : " + getNom() + ' '+getPrenom());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
