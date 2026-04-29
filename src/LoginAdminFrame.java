import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LoginAdminFrame extends JFrame {

    // ── Palette claire (thème coworking) ─────────────────────
    private static final Color WHITE        = new Color(255, 255, 255);
    private static final Color BG_LEFT      = new Color(247, 246, 243);
    private static final Color TEXT_DARK    = new Color(44,  44,  42);
    private static final Color TEXT_MED     = new Color(68,  68,  65);
    private static final Color TEXT_MUTED   = new Color(136, 135, 128);
    private static final Color TEXT_HINT    = new Color(180, 178, 169);
    private static final Color BORDER_CLR   = new Color(232, 230, 224);
    private static final Color GREEN        = new Color(29,  158, 117);
    private static final Color GREEN_DARK   = new Color(15,  110, 86);
    private static final Color GREEN_LIGHT  = new Color(225, 245, 238);
    private static final Color AMBER_LIGHT  = new Color(250, 238, 218);
    private static final Color BLUE_LIGHT   = new Color(230, 241, 251);
    private static final Color ERROR_CLR    = new Color(226, 75,  74);

    private static final Font FONT_JAKARTA_14 = new Font("Segoe UI", Font.PLAIN,  14);
    private static final Font FONT_JAKARTA_13 = new Font("Segoe UI", Font.PLAIN,  13);
    private static final Font FONT_JAKARTA_12 = new Font("Segoe UI", Font.PLAIN,  12);
    private static final Font FONT_JAKARTA_11 = new Font("Segoe UI", Font.PLAIN,  11);
    private static final Font FONT_JAKARTA_10 = new Font("Segoe UI", Font.PLAIN,  10);
    private static final Font FONT_BOLD_22    = new Font("Segoe UI", Font.BOLD,   22);
    private static final Font FONT_BOLD_20    = new Font("Segoe UI", Font.BOLD,   20);
    private static final Font FONT_BOLD_13    = new Font("Segoe UI", Font.BOLD,   13);
    private static final Font FONT_BOLD_12    = new Font("Segoe UI", Font.BOLD,   12);

    // ── Composants du formulaire ──────────────────────────────
    private JTextField     emailField;
    private JPasswordField pwField;
    private JLabel         errorLabel;
    private JButton        loginBtn;
    private final AdminDAO adminDAO = new AdminDAO();

    public LoginAdminFrame() {
        setTitle("CoWork Space — Administration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 580);
        setMinimumSize(new Dimension(860, 540));
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(860, 540));

        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setBackground(WHITE);
        setContentPane(root);

        root.add(buildLeft());
        root.add(buildRight());

        setVisible(true);
    }

    // ═══════════════════════════════════════════════════════════
    //  PANNEAU GAUCHE — branding + stats
    // ═══════════════════════════════════════════════════════════
    private JPanel buildLeft() {
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(BG_LEFT);
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        p.setLayout(new BorderLayout());
        p.setBorder(new EmptyBorder(32, 28, 28, 28));
        p.setBackground(BG_LEFT);
        p.setOpaque(true);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        top.add(buildBrand());
        top.add(Box.createVerticalStrut(28));
        top.add(makeLeftTitle("Votre espace de\ntravail collaboratif"));
        top.add(Box.createVerticalStrut(10));
        top.add(makeLeftSub("Gérez vos membres, réservations\net espaces depuis un seul tableau de bord."));
        top.add(Box.createVerticalStrut(22));
        top.add(buildSpaceCard(GREEN_LIGHT,  "Espaces de bureaux",        "Open space, bureaux privés, salles"));
        top.add(Box.createVerticalStrut(8));
        top.add(buildSpaceCard(AMBER_LIGHT, "142 membres actifs",         "Freelances, startups, équipes"));
        top.add(Box.createVerticalStrut(8));
        top.add(buildSpaceCard(BLUE_LIGHT,  "Réservations en temps réel", "Disponibilité instantanée"));

        p.add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setOpaque(false);

        bottom.add(buildStatRow());
        bottom.add(Box.createVerticalStrut(14));
        JLabel footer = new JLabel("© 2026 CoWork Space — Tous droits réservés");
        footer.setFont(FONT_JAKARTA_10);
        footer.setForeground(TEXT_HINT);
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.add(footer);

        p.add(bottom, BorderLayout.SOUTH);
        return p;
    }

    private JPanel buildBrand() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dot = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GREEN);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2.dispose();
            }
        };
        dot.setPreferredSize(new Dimension(10, 10));
        dot.setMinimumSize(new Dimension(10, 10));
        dot.setMaximumSize(new Dimension(10, 10));
        dot.setOpaque(false);

        JLabel name = new JLabel("  CoWork Space");
        name.setFont(FONT_BOLD_13);
        name.setForeground(TEXT_DARK);

        p.add(dot);
        p.add(name);
        return p;
    }

    private JLabel makeLeftTitle(String text) {
        JLabel l = new JLabel("<html>" + text.replace("\n", "<br>") + "</html>");
        l.setFont(FONT_BOLD_22);
        l.setForeground(TEXT_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel makeLeftSub(String text) {
        JLabel l = new JLabel("<html>" + text.replace("\n", "<br>") + "</html>");
        l.setFont(FONT_JAKARTA_13);
        l.setForeground(TEXT_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JPanel buildSpaceCard(Color iconBg, String title, String desc) {
        JPanel card = new JPanel(new BorderLayout(12, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(BORDER_CLR);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(10, 12, 10, 12));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        String iconText;

        if (title.toLowerCase().contains("bureau")) iconText = "🏢";
        else if (title.toLowerCase().contains("membre")) iconText = "👥";
        else if (title.toLowerCase().contains("réservation")) iconText = "📅";
        else iconText = "📊";

// Icône dynamique + fond coloré
        JLabel icon = new JLabel(iconText, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // fond dynamique conservé
                g2.setColor(iconBg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        icon.setPreferredSize(new Dimension(36, 36));
        icon.setOpaque(false);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        icon.setForeground(new Color(30, 30, 30));
        icon.setPreferredSize(new Dimension(36, 36));
        icon.setMinimumSize(new Dimension(36, 36));
        icon.setMaximumSize(new Dimension(36, 36));
        icon.setOpaque(false);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        JLabel tl = new JLabel(title);
        tl.setFont(FONT_BOLD_12);
        tl.setForeground(TEXT_MED);

        JLabel dl = new JLabel(desc);
        dl.setFont(FONT_JAKARTA_11);
        dl.setForeground(TEXT_MUTED);

        info.add(tl);
        info.add(Box.createVerticalStrut(2));
        info.add(dl);

        card.add(icon, BorderLayout.WEST);
        card.add(info, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildStatRow() {
        JPanel row = new JPanel(new GridLayout(1, 3, 8, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        row.add(buildStatBox("37",  "Réservations aujourd'hui"));
        row.add(buildStatBox("94%", "Taux d'occupation"));
        row.add(buildStatBox("12",  "Espaces disponibles"));
        return row;
    }

    private JPanel buildStatBox(String num, String label) {
        JPanel box = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(BORDER_CLR);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);
        box.setBackground(new Color(0,0,0,0));
        box.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel n = new JLabel(num);
        n.setFont(new Font("Segoe UI", Font.BOLD, 18));
        n.setForeground(TEXT_DARK);
        n.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel l = new JLabel("<html><div style='width:80px'>" + label + "</div></html>");
        l.setFont(FONT_JAKARTA_10);
        l.setForeground(TEXT_HINT);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(n);
        box.add(Box.createVerticalStrut(3));
        box.add(l);
        return box;
    }

    // ═══════════════════════════════════════════════════════════
    //  PANNEAU DROIT — formulaire de connexion
    // ═══════════════════════════════════════════════════════════
    private JPanel buildRight() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(WHITE);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(320, 460));

        form.add(buildBadge());
        form.add(Box.createVerticalStrut(18));

        JLabel title = new JLabel("Connexion");
        title.setFont(FONT_BOLD_20);
        title.setForeground(TEXT_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(title);

        JLabel sub = new JLabel("Identifiez-vous pour accéder au tableau de bord");
        sub.setFont(FONT_JAKARTA_12);
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(sub);
        form.add(Box.createVerticalStrut(26));

        form.add(makeFieldLabel("ADRESSE EMAIL"));
        form.add(Box.createVerticalStrut(6));
        emailField = makeTextField("admin@coworking.com");
        form.add(emailField);
        form.add(Box.createVerticalStrut(16));

        form.add(makeFieldLabel("MOT DE PASSE"));
        form.add(Box.createVerticalStrut(6));
        pwField = makePasswordField();
        form.add(pwField);
        form.add(Box.createVerticalStrut(8));

        errorLabel = new JLabel(" ");
        errorLabel.setFont(FONT_JAKARTA_12);
        errorLabel.setForeground(ERROR_CLR);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(errorLabel);
        form.add(Box.createVerticalStrut(14));

        // Bouton connexion
        loginBtn = buildLoginButton();
        form.add(loginBtn);
        form.add(Box.createVerticalStrut(14));

        // Bouton retour
        JButton retourBtn = new JButton("← Retour à l'accueil");
        retourBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        retourBtn.setForeground(TEXT_MUTED);
        retourBtn.setBorderPainted(false);
        retourBtn.setContentAreaFilled(false);
        retourBtn.setFocusPainted(false);
        retourBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        retourBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        retourBtn.addActionListener(e -> {
            dispose();
            new MainLoginFrame().setVisible(true);
        });
        form.add(retourBtn);

        outer.add(form);
        return outer;
    }

    private JPanel buildBadge() {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GREEN_LIGHT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        badge.setOpaque(false);
        badge.setBorder(new EmptyBorder(4, 10, 4, 10));
        badge.setMaximumSize(new Dimension(200, 26));
        badge.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dot = new JLabel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GREEN);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        dot.setPreferredSize(new Dimension(6, 6));
        dot.setMinimumSize(new Dimension(6, 6));
        dot.setMaximumSize(new Dimension(6, 6));
        dot.setOpaque(false);

        JLabel lbl = new JLabel("  ACCÈS ADMINISTRATEUR");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(GREEN_DARK);

        badge.add(dot);
        badge.add(lbl);
        return badge;
    }

    private JLabel makeFieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(TEXT_HINT);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField makeTextField(String placeholder) {
        JTextField f = new JTextField(22) {
            @Override protected void paintComponent(Graphics g) {
                paintInputBg(g, this);
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        styleInput(f);
        addPlaceholder(f, placeholder);
        return f;
    }

    private JPasswordField makePasswordField() {
        JPasswordField f = new JPasswordField(22) {
            @Override protected void paintComponent(Graphics g) {
                paintInputBg(g, this);
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        styleInput(f);
        f.setEchoChar('•');
        return f;
    }

    private void paintInputBg(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(WHITE);
        g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 8, 8);
        Color border = c.hasFocus() ? GREEN : BORDER_CLR;
        g2.setColor(border);
        g2.drawRoundRect(0, 0, c.getWidth()-1, c.getHeight()-1, 8, 8);
        g2.dispose();
    }

    private void styleInput(JTextField f) {
        f.setOpaque(false);
        f.setForeground(TEXT_DARK);
        f.setCaretColor(GREEN);
        f.setFont(FONT_JAKARTA_13);
        f.setBorder(new EmptyBorder(10, 14, 10, 14));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { f.repaint(); }
            public void focusLost(FocusEvent e)   { f.repaint(); }
        });
    }

    private void addPlaceholder(JTextField f, String ph) {
        f.setText(ph);
        f.setForeground(TEXT_HINT);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(ph)) { f.setText(""); f.setForeground(TEXT_DARK); }
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) { f.setText(ph); f.setForeground(TEXT_HINT); }
            }
        });
    }

    private JButton buildLoginButton() {
        JButton btn = new JButton("Se connecter  →") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isPressed() ? GREEN_DARK :
                        getModel().isRollover() ? new Color(20, 120, 90) : GREEN;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setForeground(WHITE);
        btn.setFont(FONT_BOLD_13);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBorder(new EmptyBorder(10, 0, 10, 0));
        btn.addActionListener(e -> handleLogin());
        getRootPane().setDefaultButton(btn);
        return btn;
    }

    private JPanel buildDivider() {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);




        JSeparator right = new JSeparator();
        right.setForeground(BORDER_CLR);
        row.add(right, BorderLayout.EAST);
        return row;
    }

    // ═══════════════════════════════════════════════════════════
    //  Logique de connexion
    // ═══════════════════════════════════════════════════════════
    private void handleLogin() {
        String email = emailField.getText().trim();
        String mdp   = new String(pwField.getPassword());

        errorLabel.setText(" ");

        if (email.isEmpty() || email.equals("admin@coworking.com")) {
            errorLabel.setText("Veuillez saisir votre email.");
            emailField.requestFocus();
            return;
        }
        if (mdp.isEmpty()) {
            errorLabel.setText("Veuillez saisir votre mot de passe.");
            pwField.requestFocus();
            return;
        }

        loginBtn.setEnabled(false);
        loginBtn.setText("Connexion en cours...");

        SwingWorker<Admin, Void> worker = new SwingWorker<>() {
            @Override protected Admin doInBackground() {
                return adminDAO.authentifier(email, mdp);
            }
            @Override protected void done() {
                try {
                    Admin admin = get();
                    if (admin != null) {
                        dispose();
                        ouvrirDashboard(admin);
                    } else {
                        errorLabel.setText("Email ou mot de passe incorrect.");
                    }
                } catch (Exception ex) {
                    errorLabel.setText("Erreur de connexion à la base de données.");
                } finally {
                    loginBtn.setText("Se connecter  →");
                    loginBtn.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    /**
     * Remplacez par : new AdminDashboardFrame(admin);
     */
    private void ouvrirDashboard(Admin admin) {
        SwingUtilities.invokeLater(() -> {
            dispose(); // ferme login

            AdminUI ui = new AdminUI();
            ui.setVisible(true);
        });
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(LoginAdminFrame::new);
    }
}