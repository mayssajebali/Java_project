import java.sql.Date;

public class Main {

    public static void main(String[] args) {

        Reservation r1 = new Reservation(
                Date.valueOf("2026-05-01"),
                Date.valueOf("2026-05-02"),
                "Salle",
                1,
                11,
                null
        );

        //r1.ajouterReservation();


        Reservation r2 = new Reservation(
                Date.valueOf("2026-06-10"),
                Date.valueOf("2026-06-12"),
                "Bureau",
                2,
                null,
                1
        );

        //r2.ajouterReservation();


        r2.setDateDebut(Date.valueOf("2026-06-20"));
        r2.modifierReservation(4);


        //r1.supprimerReservation(3);
        ConnexionDB.closeConnection();
    }
}

