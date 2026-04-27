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
    
    public static ArrayList<String> leggiStudenti(String idClasse){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:scuola.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nome, cognome, id_classe FROM alunni");
            
            ArrayList<String> studenti = new ArrayList();
            
            while(rs.next()){
                if(rs.getString("id_classe").equals(idClasse))
                    studenti.add(rs.getString("nome") + " " + rs.getString("cognome"));
            }
            
            return studenti;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
