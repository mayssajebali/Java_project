import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

public class Reservation {

    private int id;
    private Date dateDebut;
    private Date dateFin;
    private String type;

    private int idMembre;
    private Integer idSalleReunion;
    private Integer idBureau;

    public Reservation(Date dateDebut, Date dateFin, String type,
                       int idMembre, Integer idSalleReunion, Integer idBureau) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
        this.idMembre = idMembre;
        this.idSalleReunion = idSalleReunion;
        this.idBureau = idBureau;
    }

    public int getId() {
        return id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getType() {
        return type;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public Integer getIdSalleReunion() {
        return idSalleReunion;
    }

    public Integer getIdBureau() {
        return idBureau;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public void setIdSalleReunion(Integer idSalleReunion) {
        this.idSalleReunion = idSalleReunion;
    }

    public void setIdBureau(Integer idBureau) {
        this.idBureau = idBureau;
    }

    //Ajouter une réservation
    public void ajouterReservation() {

        Connection conn = ConnexionDB.getConnection();

        String sql = "INSERT INTO reservation (dateDebut, dateFin, type, idMembre, idSalleReunion, idBureau) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            // Vérification logique
            if (idSalleReunion == null && idBureau == null) {
                throw new IllegalArgumentException("Une réservation doit avoir une salle ou un bureau");
            }

            if (type.equalsIgnoreCase("Salle")) {
                idBureau = null;
            } else if (type.equalsIgnoreCase("Bureau")) {
                idSalleReunion = null;
            }

            ps.setDate(1, dateDebut);
            ps.setDate(2, dateFin);
            ps.setString(3, type);
            ps.setInt(4, idMembre);

            // Gestion NULL
            if (idSalleReunion != null) {
                ps.setInt(5, idSalleReunion);
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            if (idBureau != null) {
                ps.setInt(6, idBureau);
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();

            System.out.println("Réservation ajoutée avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Modifier une réservation
    public void modifierReservation(int id) {

        Connection conn = ConnexionDB.getConnection();

        String sql = "UPDATE reservation SET dateDebut=?, dateFin=?, type=?, idMembre=?, idSalleReunion=?, idBureau=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, dateDebut);
            ps.setDate(2, dateFin);
            ps.setString(3, type);
            ps.setInt(4, idMembre);

            if (idSalleReunion != null) {
                ps.setInt(5, idSalleReunion);
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            if (idBureau != null) {
                ps.setInt(6, idBureau);
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            ps.setInt(7, id);

            ps.executeUpdate();

            System.out.println("Réservation modifiée : " + id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Supprimer une réservation
    public void supprimerReservation(int id) {

        Connection conn = ConnexionDB.getConnection();

        String sql = "DELETE FROM reservation WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Réservation supprimée : " + id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}