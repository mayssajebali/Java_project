import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MembreGUI extends JFrame {

    private JTextField tfNom, tfPrenom, tfEmail, tfMotDePasse, tfId;
    private JComboBox<String> cbType;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnAnnuler;

    private JTable table;
    private DefaultTableModel model;

    public MembreGUI() {
        setTitle("Gestion des Membres");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // PANEL FORMULAIRE
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 8, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        tfId = new JTextField();
        tfId.setEditable(false); //

        tfNom = new JTextField();
        tfPrenom = new JTextField();
        tfEmail = new JTextField();
        tfMotDePasse = new JTextField();

        cbType = new JComboBox<>(new String[]{"JOURNALIER", "MENSUEL", "ANNUEL"});

        panelForm.add(new JLabel("ID :")); panelForm.add(tfId);
        panelForm.add(new JLabel("Nom :")); panelForm.add(tfNom);
        panelForm.add(new JLabel("Prénom :")); panelForm.add(tfPrenom);
        panelForm.add(new JLabel("Email :")); panelForm.add(tfEmail);
        panelForm.add(new JLabel("Mot de Passe :")); panelForm.add(tfMotDePasse);
        panelForm.add(new JLabel("Type :")); panelForm.add(cbType);

        // BOUTONS
        JPanel panelBtn = new JPanel();
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnAnnuler = new JButton("Annuler");

        panelBtn.add(btnAjouter);
        panelBtn.add(btnModifier);
        panelBtn.add(btnSupprimer);
        panelBtn.add(btnAnnuler);

        // TABLE
        String[] colonnes = {"ID", "Nom", "Prénom", "Email", "Abonnement"};
        model = new DefaultTableModel(colonnes, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        // LAYOUT PRINCIPAL
        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBtn, BorderLayout.SOUTH);

        // EVENTS
        btnAjouter.addActionListener(e -> ajouterMembre());
        btnModifier.addActionListener(e -> modifierMembre());
        btnSupprimer.addActionListener(e -> supprimerMembre());
        btnAnnuler.addActionListener(e -> viderChamps());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();

                tfId.setText(model.getValueAt(row, 0).toString());
                tfNom.setText(model.getValueAt(row, 1).toString());
                tfPrenom.setText(model.getValueAt(row, 2).toString());
                tfEmail.setText(model.getValueAt(row, 3).toString());
                cbType.setSelectedItem(model.getValueAt(row, 4).toString());
            }
        });

        chargerMembres();

        setVisible(true);
    }

    // Charger les membres dans le tableau
    private void chargerMembres() {
        model.setRowCount(0);

        Connection conn = ConnexionDB.getConnection();

        String sql = "SELECT * FROM membre";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("typeAbonnement")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Ajouter
    private void ajouterMembre() {
        String nom = tfNom.getText().trim();
        String prenom = tfPrenom.getText().trim();
        String email = tfEmail.getText().trim();
        String mdp = tfMotDePasse.getText().trim();
        String type = (String) cbType.getSelectedItem();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis !");
            return;
        }

        Membre m = new Membre(nom, prenom, email, mdp, type);
        m.ajouterMembre();

        chargerMembres();
        viderChamps();

        JOptionPane.showMessageDialog(this, "Membre ajouté !");
    }

    // Modifier
    private void modifierMembre() {
        try {
            int id = Integer.parseInt(tfId.getText());

            Membre m = new Membre(
                    tfNom.getText(),
                    tfPrenom.getText(),
                    tfEmail.getText(),
                    tfMotDePasse.getText(),
                    (String) cbType.getSelectedItem()
            );

            m.modifierMembre(id);
            chargerMembres();

            JOptionPane.showMessageDialog(this, "Membre modifié !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un membre !");
        }
    }

    // Supprimer
    private void supprimerMembre() {
        try {
            int id = Integer.parseInt(tfId.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ce membre ?");
            if (confirm != JOptionPane.YES_OPTION) return;
            Membre m = new Membre("", "", "", "", "");
            m.supprimerMembre(id);

            chargerMembres();
            viderChamps();

            JOptionPane.showMessageDialog(this, "Membre supprimé !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un membre !");
        }
    }

    // vider champs
    private void viderChamps() {
        tfId.setText("");
        tfNom.setText("");
        tfPrenom.setText("");
        tfEmail.setText("");
        tfMotDePasse.setText("");
        cbType.setSelectedIndex(0);
        table.clearSelection();
    }

    public static void main(String[] args) {
        new MembreGUI();
    }
}