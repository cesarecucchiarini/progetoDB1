/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package progettodb;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author cucchiarini.cesare
 */
public class FrameProgetto extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrameProgetto.class.getName());
    
    private DefaultListModel<String> modelStudenti;
    private DefaultComboBoxModel<String> modelClassi;
    private JComboBox<String> boxClassi;
    private JList<String> listStudenti;
    private JButton toggleButton;
    private boolean modalitaGite = false;
    private ItemListener listenerClassi = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Object item = e.getItem();
                String classe = ((String) item).split(" ")[0];
                modelStudenti.clear();
                aggiornaListaStudenti(DBManager.leggiStudentiClasse(classe));
        }};
    private ItemListener listenerGite = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String gita = (String) e.getItem();
                String idGita = gita.split(" ")[0];
                modelStudenti.clear();
                aggiornaListaStudenti(DBManager.leggiStudentiGite(idGita));
            }
        };
    /**
     * Creates new form FrameProgetto
     */
    public FrameProgetto() {
        initComponents();
        
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        
        JPanel panel  = new JPanel();
        panel.setBackground(new java.awt.Color(245, 247, 250));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        
        JLabel titolo = new JLabel("Gestione classi e gite");
        titolo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titolo.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel sottotitolo = new JLabel("Seleziona una classe o una gita per vedere gli studenti");
        sottotitolo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        sottotitolo.setAlignmentX(CENTER_ALIGNMENT);
        
        modelClassi = new DefaultComboBoxModel<>();
        boxClassi = new JComboBox<>(modelClassi);
        boxClassi.setMaximumSize(new java.awt.Dimension(450, 35));
        boxClassi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boxClassi.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
            ) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (modalitaGite && value instanceof String) {
                    String testo = (String) value;
                    String[] parti = testo.split(" ", 2);
                    setText(parti.length == 2 ? parti[1] : testo);
                }
                return this;
            }
        });
        boxClassi.addItemListener(listenerClassi);
        
        modelStudenti = new DefaultListModel<>();
        listStudenti = new JList<>(modelStudenti);
        listStudenti.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listStudenti.setFixedCellHeight(28);
        listStudenti.setSelectionBackground(new java.awt.Color(0, 120, 215));
        
        JScrollPane scrollPane = new JScrollPane(listStudenti);
        scrollPane.setMaximumSize(new java.awt.Dimension(900, 380));
        scrollPane.setBorder(BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        
        toggleButton = new JButton("Passa a modalità gite");
        toggleButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        toggleButton.setAlignmentX(CENTER_ALIGNMENT);
        toggleButton.addActionListener(e -> {
            modalitaGite = !modalitaGite;
            cambiaModalita();
        });
        
        panel.add(Box.createVerticalGlue());
        panel.add(titolo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(sottotitolo);
        panel.add(Box.createVerticalStrut(20));
        panel.add(toggleButton);
        panel.add(Box.createVerticalStrut(18));
        panel.add(boxClassi);
        panel.add(Box.createVerticalStrut(18));
        panel.add(scrollPane);
        panel.add(Box.createVerticalGlue());
        
        this.add(panel, BorderLayout.CENTER);
        
        aggiornaComboClassi(DBManager.leggiClassi());
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }
    
    private void cambiaModalita() {
        modelStudenti.clear();
        modelClassi.removeAllElements();
        
        if (modalitaGite) {
            boxClassi.removeItemListener(listenerClassi);
            boxClassi.addItemListener(listenerGite);
            aggiornaComboClassi(DBManager.leggiGite());
            toggleButton.setText("Torna a modalità classi");
        } else {
            boxClassi.removeItemListener(listenerGite);
            boxClassi.addItemListener(listenerClassi);
            aggiornaComboClassi(DBManager.leggiClassi());
            toggleButton.setText("Passa a modalità gite");
        }
    }
    
    private void aggiornaComboClassi(List<String> valori) {
        if (valori != null) {
            modelClassi.addAll(valori);
        }
    }
    
    private void aggiornaListaStudenti(List<String> valori) {
        if (valori != null) {
            modelStudenti.addAll(valori);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrameProgetto().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
