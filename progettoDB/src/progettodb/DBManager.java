/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progettodb;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author cucchiarini.cesare
 */
public class DBManager {
    private static Connection connection;
    
    public static ArrayList<String> leggiClassi(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:scuola.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM classi");
            
            ArrayList<String> classi = new ArrayList();
            
            while(rs.next()){
                classi.add(rs.getString("id_classe") + " " + rs.getString("indirizzo"));
            }
            
            return classi;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<String> leggiStudentiClasse(String idClasse){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:scuola.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM alunni WHERE id_classe = \""+idClasse+"\"");
            
            ArrayList<String> studenti = new ArrayList();
            
            while(rs.next()){

                    studenti.add(rs.getString("id_classe") + " " + rs.getString("nome") + " " + rs.getString("cognome"));
            }
            
            return studenti;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<String> leggiGite(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:scuola.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_gita, destinazione FROM gite");
            
            ArrayList<String> classi = new ArrayList();
            
            while(rs.next()){
                classi.add(rs.getString("id_gita") + " " + rs.getString("destinazione"));
            }
            
            return classi;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<String> leggiStudentiGite(String idGita){
        try (
            Connection conn = DriverManager.getConnection("jdbc:sqlite:scuola.db");
            PreparedStatement ps = conn.prepareStatement(
                "SELECT alunni.nome, alunni.cognome, gite.prezzo, gite.destinazione " +
                "FROM partecipanti " +
                "JOIN alunni ON partecipanti.id_alunno = alunni.id_alunno " +
                "JOIN gite ON partecipanti.id_gita = gite.id_gita " +
                "WHERE partecipanti.id_gita = ? " +
                "ORDER BY alunni.cognome, alunni.nome"
            )
        ) {
            ps.setString(1, idGita);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> studenti = new ArrayList<>();
            
            while(rs.next()){
                studenti.add(
                    rs.getString("nome") + " " + rs.getString("cognome") +
                    " - " + rs.getString("destinazione") +
                    " (EUR " + rs.getString("prezzo") + ")"
                );
            }
            return studenti;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<String> leggiStudentiGita(String idGita){
        return leggiStudentiGite(idGita);
    }
}
