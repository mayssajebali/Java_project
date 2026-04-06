public class Main {

    public static void main(String[] args) {

        Membre m = new Membre("Arfaoui","Takwa", "takwa@gmail.com", "2587", "Journalier");
        m.ajouterMembre();
        m.setNom("Aarfaoui");
        m.modifierMembre(4);
        m.supprimerMembre(4);

        // Fermer la connexion
        ConnexionDB.closeConnection();
    }
}
