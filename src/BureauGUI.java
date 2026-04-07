import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BureauGUI extends JFrame {

    private JTextField tfId, tfNom, tfTarif;
    private JCheckBox cbDisponible;

    private JButton btnAjouter, btnModifier, btnSupprimer, btnAnnuler;

    private JTable table;
    private
    DefaultTableModel model;

    public BureauGUI() {

        setTitle("Gestion des Bureaux");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // FORM
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 8, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        tfId = new JTextField();
        tfId.setEditable(false);

        tfNom = new JTextField();
        tfTarif = new JTextField();

        cbDisponible = new JCheckBox("Disponible");

        panelForm.add(new JLabel("ID :")); panelForm.add(tfId);
        panelForm.add(new JLabel("Nom :")); panelForm.add(tfNom);
        panelForm.add(new JLabel("Tarif :")); panelForm.add(tfTarif);
        panelForm.add(new JLabel("")); panelForm.add(cbDisponible);

        // BUTTONS
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
        String[] colonnes = {"ID", "Nom", "Disponible", "Tarif"};
        model = new DefaultTableModel(colonnes, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        // LAYOUT
        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBtn, BorderLayout.SOUTH);

        // EVENTS

        btnAjouter.addActionListener(e -> ajouterBureau());
        btnModifier.addActionListener(e -> modifierBureau());
        btnSupprimer.addActionListener(e -> supprimerBureau());
        btnAnnuler.addActionListener(e -> viderChamps());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();

                tfId.setText(model.getValueAt(row, 0).toString());
                tfNom.setText(model.getValueAt(row, 1).toString());
                cbDisponible.setSelected((Boolean) model.getValueAt(row, 2));
                tfTarif.setText(model.getValueAt(row, 3).toString());
            }
        });

        chargerBureaux();

        setVisible(true);
    }

    //LOAD
    private void chargerBureaux() {
        model.setRowCount(0);

        Connection conn = ConnexionDB.getConnection();

        String sql = "SELECT * FROM bureau";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getBoolean("disponible"),
                        rs.getDouble("tarif")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ADD
    private void ajouterBureau() {
        try {
            String nom = tfNom.getText();
            boolean dispo = cbDisponible.isSelected();
            double tarif = Double.parseDouble(tfTarif.getText());

            Bureau b = new Bureau(nom, dispo, tarif);
            b.ajouterBureau();

            chargerBureaux();
            viderChamps();

            JOptionPane.showMessageDialog(this, "Bureau ajouté !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur !");
        }
    }

    // UPDATE
    private void modifierBureau() {
        try {
            int id = Integer.parseInt(tfId.getText());

            Bureau b = new Bureau(
                    tfNom.getText(),
                    cbDisponible.isSelected(),
                    Double.parseDouble(tfTarif.getText())
            );

            b.modifierBureau(id);
            chargerBureaux();

            JOptionPane.showMessageDialog(this, "Bureau modifié !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un bureau !");
        }
    }

    //DELETE
    private void supprimerBureau() {
        try {
            int id = Integer.parseInt(tfId.getText());

            int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ce bureau ?");
            if (confirm != JOptionPane.YES_OPTION) return;

            Bureau b = new Bureau("", false, 0);
            b.supprimerBureau(id);

            chargerBureaux();
            viderChamps();

            JOptionPane.showMessageDialog(this, "Bureau supprimé !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un bureau !");
        }
    }

    // CLEAR
    private void viderChamps() {
        tfId.setText("");
        tfNom.setText("");
        tfTarif.setText("");
        cbDisponible.setSelected(false);
        table.clearSelection();
    }

    // MAIN
    public static void main(String[] args) {
        new BureauGUI();
    }
}