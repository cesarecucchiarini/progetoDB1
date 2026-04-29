package progettodb;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FrameInserimentoStudente extends JFrame {

    private final JTextField nomeField;
    private final JTextField cognomeField;
    private final JComboBox<String> classiComboBox;
    private final DefaultComboBoxModel<String> modelClassi;

    public FrameInserimentoStudente() {
        setTitle("Inserimento studente");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));

        JPanel panel = new JPanel();
        panel.setBackground(new java.awt.Color(245, 247, 250));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JLabel titolo = new JLabel("Nuovo studente");
        titolo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titolo.setAlignmentX(CENTER_ALIGNMENT);

        JPanel nomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nomePanel.setBackground(new java.awt.Color(245, 247, 250));
        JLabel nomeLabel = new JLabel("Nome");
        nomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nomeField = new JTextField(20);
        nomeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nomePanel.add(nomeLabel);
        nomePanel.add(nomeField);

        JPanel cognomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cognomePanel.setBackground(new java.awt.Color(245, 247, 250));
        JLabel cognomeLabel = new JLabel("Cognome");
        cognomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cognomeField = new JTextField(20);
        cognomeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cognomePanel.add(cognomeLabel);
        cognomePanel.add(cognomeField);

        JPanel classePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        classePanel.setBackground(new java.awt.Color(245, 247, 250));
        JLabel classeLabel = new JLabel("Classe");
        classeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        modelClassi = new DefaultComboBoxModel<>();
        classiComboBox = new JComboBox<>(modelClassi);
        classiComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        classiComboBox.setPrototypeDisplayValue("Classe 5A - Indirizzo Tecnico");
        classePanel.add(classeLabel);
        classePanel.add(classiComboBox);

        JButton salvaButton = new JButton("Salva");
        salvaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        salvaButton.setAlignmentX(CENTER_ALIGNMENT);
        salvaButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String idClasse = (String) classiComboBox.getSelectedItem();
            
            if (nome.isEmpty() || cognome.isEmpty() || idClasse == null || idClasse.isBlank()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Compila nome, cognome e seleziona una classe.",
                    "Dati mancanti",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            boolean inserito = DBManager.aggiungiStudente(nome, cognome, idClasse);
            if (inserito) {
                JOptionPane.showMessageDialog(
                    this,
                    "Studente inserito correttamente.",
                    "Operazione completata",
                    JOptionPane.INFORMATION_MESSAGE
                );
                nomeField.setText("");
                cognomeField.setText("");
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Errore durante l'inserimento dello studente.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        JButton cambiaFormButton = new JButton("Torna al form principale");
        cambiaFormButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cambiaFormButton.setAlignmentX(CENTER_ALIGNMENT);
        cambiaFormButton.addActionListener(e -> {
            new FrameProgetto().setVisible(true);
            dispose();
        });

        panel.add(titolo);
        panel.add(Box.createVerticalStrut(20));
        panel.add(nomePanel);
        panel.add(cognomePanel);
        panel.add(classePanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(salvaButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cambiaFormButton);

        add(panel, BorderLayout.CENTER);

        aggiornaClassi(DBManager.leggiClassi());
        pack();
        setLocationRelativeTo(null);
    }

    private void aggiornaClassi(List<String> classi) {
        if (classi != null) {
            modelClassi.addAll(classi);
        }
    }
}
