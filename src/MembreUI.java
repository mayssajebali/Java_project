import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class MembreUI extends JFrame {

    // Palette identique à AdminUI
    private static final Color L_SIDEBAR  = new Color(30,  36,  51);
    private static final Color L_ACT      = new Color(37,  99,  235);
    private static final Color L_NAV_TEXT = new Color(156, 163, 175);
    private static final Color L_BG       = new Color(249, 250, 251);
    private static final Color L_WHITE    = Color.WHITE;
    private static final Color L_BORDER   = new Color(229, 231, 235);
    private static final Color L_TEXT     = new Color(17,  24,  39);
    private static final Color L_SUBTEXT  = new Color(107, 114, 128);
    private static final Color GREEN      = new Color(22,  163, 74);
    private static final Color BLUE       = new Color(37,  99,  235);
    private static final Color ORANGE     = new Color(249, 115, 22);
    private static final Color RED_LOGOUT = new Color(220, 38,  38);
    private static final Color L_SEL      = new Color(219, 234, 254);
    private static final Color L_ROW_ALT  = new Color(249, 250, 251);
    private static final Color WHITE      = Color.WHITE;

    private final Membre membre;
    private final ReservationDAO    resDAO = new ReservationDAO();
    private final SalleDeReunionDAO salDAO = new SalleDeReunionDAO();
    private final BureauDAO         burDAO = new BureauDAO();

    private JLabel lblStatus;
    private String activeSection = "bureaux";

    public MembreUI(Membre membre) {
        this.membre = membre;
        setTitle("CoWork Space — Espace Membre");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        getContentPane().removeAll();
        add(buildSidebar(),   BorderLayout.WEST);
        add(buildMain(),      BorderLayout.CENTER);
        revalidate(); repaint();
    }

    // ── SIDEBAR ──────────────────────────────────────────────
    private JPanel buildSidebar() {
        JPanel p = new JPanel();
        p.setBackground(L_SIDEBAR);
        p.setPreferredSize(new Dimension(210, 0));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20, 12, 20, 12));

        JLabel logo = new JLabel("CoWorking");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        logo.setForeground(WHITE);
        logo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel logoSub = new JLabel("Espace Membre");
        logoSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        logoSub.setForeground(new Color(107, 114, 128));
        logoSub.setAlignmentX(LEFT_ALIGNMENT);
        logoSub.setBorder(BorderFactory.createEmptyBorder(2, 0, 14, 0));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(55, 65, 81));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        p.add(logo); p.add(logoSub); p.add(sep);
        p.add(Box.createVerticalStrut(12));
        p.add(sectionLabel("MENU"));

        // Avatar + nom
        JPanel avatar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        avatar.setOpaque(false);
        avatar.setAlignmentX(LEFT_ALIGNMENT);
        avatar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        JLabel av = new JLabel(membre.getNom().substring(0,1).toUpperCase(), SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(L_ACT); g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose(); super.paintComponent(g);
            }
        };
        av.setFont(new Font("Segoe UI", Font.BOLD, 13));
        av.setForeground(WHITE);
        av.setPreferredSize(new Dimension(30, 30));
        av.setOpaque(false);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setOpaque(false);
        JLabel nm = new JLabel(membre.getNom());
        nm.setFont(new Font("Segoe UI", Font.BOLD, 12));
        nm.setForeground(WHITE);
        JLabel ab = new JLabel(membre.getTypeAbonnement());
        ab.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        ab.setForeground(L_NAV_TEXT);
        namePanel.add(nm); namePanel.add(ab);

        avatar.add(av); avatar.add(namePanel);
        p.add(avatar);
        p.add(Box.createVerticalStrut(8));

        // Nav items
        p.add(navBtn("🪑  Bureaux",         "bureaux"));
        p.add(Box.createVerticalStrut(4));
        p.add(navBtn("🏢  Salles de réunion", "salles"));
        p.add(Box.createVerticalStrut(4));

        p.add(Box.createVerticalGlue());
        p.add(sectionLabel("COMPTE"));

        // Déconnexion
        JButton btnLogout = new JButton("⬡  Déconnexion");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnLogout.setForeground(new Color(239, 68, 68));
        btnLogout.setBackground(new Color(45, 55, 72));
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setAlignmentX(LEFT_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnLogout.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btnLogout.addActionListener(e -> { dispose(); new MainLoginFrame().setVisible(true); });
        p.add(btnLogout);

        return p;
    }

    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(new Color(75, 85, 99));
        l.setAlignmentX(LEFT_ALIGNMENT);
        l.setBorder(BorderFactory.createEmptyBorder(8, 4, 4, 0));
        l.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        return l;
    }

    private JButton navBtn(String label, String section) {
        boolean active = section.equals(activeSection);
        JButton btn = new JButton(label);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(active ? WHITE : L_NAV_TEXT);
        btn.setBackground(active ? L_ACT : L_SIDEBAR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> { activeSection = section; buildUI(); });
        return btn;
    }

    // ── MAIN ─────────────────────────────────────────────────
    private JPanel buildMain() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(L_BG);
        p.add(buildTopBar(),    BorderLayout.NORTH);
        p.add(buildContent(),   BorderLayout.CENTER);
        p.add(buildStatusBar(), BorderLayout.SOUTH);
        return p;
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(L_WHITE);
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, L_BORDER),
                BorderFactory.createEmptyBorder(14, 20, 14, 20)));

        String sectionTitle = activeSection.equals("bureaux") ? "Bureaux" : "Salles de réunion";
        String sectionSub   = activeSection.equals("bureaux")
                ? "Historique de vos réservations de bureaux"
                : "Historique de vos réservations de salles";

        JLabel title = new JLabel(sectionTitle);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(L_TEXT);

        JLabel sub = new JLabel(sectionSub);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(L_SUBTEXT);

        JPanel titleGroup = new JPanel(new GridLayout(2, 1, 0, 2));
        titleGroup.setBackground(L_WHITE);
        titleGroup.add(title); titleGroup.add(sub);

        bar.add(titleGroup, BorderLayout.WEST);
        return bar;
    }

    private JPanel buildContent() {
        JPanel p = new JPanel(new BorderLayout(0, 16));
        p.setBackground(L_BG);
        p.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        // Stats
        List<Reservation> all = resDAO.getReservationsParMembre(membre.getId());
        long cntBureau = all.stream().filter(r -> "Bureau".equalsIgnoreCase(r.getType())).count();
        long cntSalle  = all.stream().filter(r -> "Salle".equalsIgnoreCase(r.getType())).count();

        JLabel lblTotal  = new JLabel(String.valueOf(all.size()));
        JLabel lblBureau = new JLabel(String.valueOf(cntBureau));
        JLabel lblSalle  = new JLabel(String.valueOf(cntSalle));

        JPanel stats = new JPanel(new GridLayout(1, 3, 12, 0));
        stats.setOpaque(false);
        stats.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        stats.add(statCard("Total réservations", lblTotal,  BLUE));
        stats.add(statCard("Bureaux réservés",   lblBureau, ORANGE));
        stats.add(statCard("Salles réservées",   lblSalle,  GREEN));

        // Table
        JScrollPane table = activeSection.equals("bureaux")
                ? buildTable("Bureau") : buildTable("Salle");

        p.add(stats, BorderLayout.NORTH);
        p.add(table, BorderLayout.CENTER);
        return p;
    }

    private JPanel statCard(String label, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1, 0, 6));
        card.setBackground(L_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(L_BORDER),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(L_SUBTEXT);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        card.add(lbl); card.add(valueLabel);
        return card;
    }

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        bar.setBackground(L_WHITE);
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, L_BORDER));
        JPanel dot = new JPanel();
        dot.setBackground(GREEN);
        dot.setPreferredSize(new Dimension(8, 8));
        lblStatus = new JLabel("  Bienvenue, " + membre.getNom() + " !");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblStatus.setForeground(L_SUBTEXT);
        bar.add(dot); bar.add(lblStatus);
        return bar;
    }

    private JScrollPane buildTable(String type) {
        String[] cols = {"ID", "Espace", "Date début", "Date fin", "Durée"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        List<Reservation>    reservations = resDAO.getReservationsParMembre(membre.getId());
        List<SalleDeReunion> salles        = salDAO.getToutesLesSalles();
        List<Bureau>         bureaux       = burDAO.getTousLesBureaux();

        for (Reservation r : reservations) {
            if (!r.getType().equalsIgnoreCase(type)) continue;

            String nomEspace = "";
            if ("Salle".equalsIgnoreCase(type) && r.getIdSalleReunion() != null) {
                nomEspace = salles.stream()
                        .filter(s -> s.getId() == r.getIdSalleReunion())
                        .map(SalleDeReunion::getNom)
                        .findFirst().orElse("Salle #" + r.getIdSalleReunion());
            } else if ("Bureau".equalsIgnoreCase(type) && r.getIdBureau() != null) {
                nomEspace = bureaux.stream()
                        .filter(b -> b.getId() == r.getIdBureau())
                        .map(Bureau::getNom)
                        .findFirst().orElse("Bureau #" + r.getIdBureau());
            }

            long diffMs = r.getDateFin().getTime() - r.getDateDebut().getTime();
            long jours  = Math.max(1, (long) Math.ceil(diffMs / (1000.0 * 60 * 60 * 24)));
            String duree = jours + (jours > 1 ? " jours" : " jour");

            model.addRow(new Object[]{
                    r.getId(), nomEspace,
                    r.getDateDebut().toString().substring(0, 16),
                    r.getDateFin().toString().substring(0, 16),
                    duree
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(L_WHITE);
        table.setSelectionBackground(L_SEL);
        table.setSelectionForeground(L_TEXT);

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 11));
        th.setBackground(L_BG);
        th.setForeground(L_SUBTEXT);
        th.setPreferredSize(new Dimension(0, 38));
        th.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, L_BORDER));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                setForeground(L_TEXT);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                setHorizontalAlignment(col == 1 ? LEFT : CENTER);
                setBackground(sel ? L_SEL : (row % 2 == 0 ? L_WHITE : L_ROW_ALT));
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(L_BORDER));
        scroll.getViewport().setBackground(L_WHITE);
        return scroll;
    }
}