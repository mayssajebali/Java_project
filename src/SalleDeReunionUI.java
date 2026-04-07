import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalleDeReunionUI extends JFrame {

    private JTextField txtNom, txtCapacite, txtTarif, txtId;
    private JCheckBox chkDisponible;
    private JTable table;
    private DefaultTableModel tableModel;

    public SalleDeReunionUI() {
        setTitle("Gestion Salle de Réunion");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Pannel formulaire
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Formulaire"));
        panelForm.setPreferredSize(new Dimension(300, 0));

        panelForm.add(new JLabel("ID"));
        txtId = new JTextField();
        panelForm.add(txtId);
        txtId.setEditable(false);

        panelForm.add(new JLabel("Nom :"));
        txtNom = new JTextField();
        panelForm.add(txtNom);

        panelForm.add(new JLabel("Capacité :"));
        txtCapacite = new JTextField();
        panelForm.add(txtCapacite);

        panelForm.add(new JLabel("Tarif :"));
        txtTarif = new JTextField();
        panelForm.add(txtTarif);

        panelForm.add(new JLabel("Disponible :"));
        chkDisponible = new JCheckBox();
        chkDisponible.setSelected(true);
        panelForm.add(chkDisponible);

        //Panel boutons
        JPanel panelBoutons = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBoutons.setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnVerifier = new JButton(" Vérifier Disponibilité");

        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnVerifier);

        //Panel gauche
        JPanel panelGauche = new JPanel(new BorderLayout());
        panelGauche.add(panelForm, BorderLayout.CENTER);
        panelGauche.add(panelBoutons, BorderLayout.SOUTH);

        //Table (droite)
        String[] colonnes = {"ID", "Nom", "Capacité", "Disponible", "Tarif"};
        tableModel = new DefaultTableModel(colonnes, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des Salles"));

        //Ajout au frame
        add(panelGauche, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // Actions boutons
        btnAjouter.addActionListener(e -> {
            try {
                String nom = txtNom.getText();
                int capacite = Integer.parseInt(txtCapacite.getText());
                double tarif = Double.parseDouble(txtTarif.getText());
                boolean dispo = chkDisponible.isSelected();

                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Le nom est obligatoire !");
                    return;
                }

                SalleDeReunion s = new SalleDeReunion(0, nom, capacite, dispo, tarif);
                s.ajouterSalle();
                chargerSalles();
                viderFormulaire();
                JOptionPane.showMessageDialog(this, "Salle ajoutée !");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capacité et Tarif doivent être des nombres !");
            }
        });

        btnModifier.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nom = txtNom.getText();
                int capacite = Integer.parseInt(txtCapacite.getText());
                double tarif = Double.parseDouble(txtTarif.getText());
                boolean dispo = chkDisponible.isSelected();

                SalleDeReunion s = new SalleDeReunion(id, nom, capacite, dispo, tarif);
                s.modifierSalle(id);
                chargerSalles();
                viderFormulaire();
                JOptionPane.showMessageDialog(this, "Salle modifiée !");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs correctement !");
            }
        });

        btnSupprimer.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                int confirm = JOptionPane.showConfirmDialog(this, "Supprimer la salle ID " + id + " ?");
                if (confirm == JOptionPane.YES_OPTION) {
                    SalleDeReunion s = new SalleDeReunion();
                    s.supprimerSalle(id);
                    chargerSalles();
                    viderFormulaire();
                    JOptionPane.showMessageDialog(this, "Salle supprimée !");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide !");
            }
        });

        btnVerifier.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                SalleDeReunion s = new SalleDeReunion();
                boolean dispo = s.verifierDisponibilite(id);
                String msg = dispo ? "Salle disponible !" : "Salle non disponible !";
                JOptionPane.showMessageDialog(this, msg);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide !");
            }
        });

        // Clic sur une ligne du tableau pour remplir le formulaire
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtNom.setText(tableModel.getValueAt(row, 1).toString());
                txtCapacite.setText(tableModel.getValueAt(row, 2).toString());
                chkDisponible.setSelected(tableModel.getValueAt(row, 3).toString().equals("oui"));
                txtTarif.setText(tableModel.getValueAt(row, 4).toString());
            }
        });

        chargerSalles();
        setVisible(true);
    }

    private void chargerSalles() {
        tableModel.setRowCount(0);
        List<SalleDeReunion> salles = SalleDeReunion.getToutesLesSalles();
        for (SalleDeReunion s : salles) {
            tableModel.addRow(new Object[]{
                    s.getId(), s.getNom(), s.getCapacite(), s.isDisponible() ? "oui" : "non", s.getTarif()
            });
        }
    }

    private void viderFormulaire() {
        txtId.setText("");
        txtNom.setText("");
        txtCapacite.setText("");
        txtTarif.setText("");
        chkDisponible.setSelected(true);
    }

    public static void main(String[] args) {
        new SalleDeReunionUI();
    }
}