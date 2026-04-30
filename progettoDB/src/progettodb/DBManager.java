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
            ResultSet rs = st.executeQuery("SELECT id_classe FROM classi");
            
            ArrayList<String> classi = new ArrayList();
            
            while(rs.next()){
                classi.add(rs.getString("id_classe"));
            }
            
            return classi;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<String> leggiStudentiClasse(String idClasse){
        try (
            Connection conn = DriverManager.getConnection("jdbc:sqlite:scuola.db");
            Statement st = conn.createStatement()
        ) {
            String idClasseSqlValue = toSqlValue(idClasse);
            ResultSet rs = st.executeQuery(
                "SELECT classi.indirizzo, alunni.nome, alunni.cognome " +
                "FROM alunni " +
                "JOIN classi ON alunni.id_classe = classi.id_classe " +
                "WHERE alunni.id_classe = " + idClasseSqlValue
            );
            ArrayList<String> studenti = new ArrayList<>();
            
            while(rs.next()){
                studenti.add(
                    rs.getString("indirizzo") + " " +
                    rs.getString("nome") + " " +
                    rs.getString("cognome")
                );
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
            Statement st = conn.createStatement()
        ) {
            String idGitaSqlValue = toSqlValue(idGita);
            ResultSet rs = st.executeQuery(
                "SELECT alunni.nome, alunni.cognome, gite.prezzo, gite.destinazione, partecipanti.pagato " +
                "FROM partecipanti " +
                "JOIN alunni ON partecipanti.id_alunno = alunni.id_alunno " +
                "JOIN gite ON partecipanti.id_gita = gite.id_gita " +
                "WHERE partecipanti.id_gita = " + idGitaSqlValue
            );
            ArrayList<String> studenti = new ArrayList<>();
            
            while(rs.next()){
                studenti.add(
                    rs.getString("nome") + " " + rs.getString("cognome") +
                    " - " + rs.getString("destinazione") +
                    " " + rs.getString("prezzo") + " "+
                    (rs.getInt("pagato") == 1 ? "pagato" : "non pagato")
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
    
    public static boolean aggiungiStudente(String nome, String cognome, String idClasse) {
        if (nome == null || cognome == null || idClasse == null
                || nome.isBlank() || cognome.isBlank() || idClasse.isBlank()) {
            return false;
        }
        
        try (
            Connection conn = DriverManager.getConnection("jdbc:sqlite:scuola.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT COALESCE(MAX(id_alunno), 0) + 1 AS nuovo_id FROM alunni")
        ) {
            if (!rs.next()) {
                return false;
            }
            int nuovoId = rs.getInt("nuovo_id");
            String queryInsert =
                "INSERT INTO alunni (id_alunno, nome, cognome, id_classe) VALUES (" +
                nuovoId + ", " +
                toSqlValue(nome) + ", " +
                toSqlValue(cognome) + ", " +
                toSqlValue(idClasse) + ")";

            return st.executeUpdate(queryInsert) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String toSqlValue(String value) {
        if (value == null) {
            return "NULL";
        }
        return "'" + value.replace("'", "''") + "'";
    }
}
