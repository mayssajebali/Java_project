public class Main {

    public static void main(String[] args) {

        // Créer un membre
        Membre m = new Membre("Ahmed","Mejri", "ahmed@gmail.com", "1234", "MENSUEL");
        m.ajouterMembre();

        // Fermer la connexion (optionnel)
        ConnexionDB.closeConnection();
    }
}
