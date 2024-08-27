/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package econometrica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.swing.JOptionPane;

/**
 *
 * @author ΧΡΗΣΤΟΣ ΜΠΑΡΜΠΑΣ - 084233
 * @author ΤΣΟΥΚΑΛΑΣ ΠΑΝΑΓΙΩΤΗΣ - 128374
 * @author ΧΑΤΖΗΚΥΡΙΑΚΙΔΟΥ ΚΥΡΙΑΚΗ - 100336
 */

public final class DbConnector {
    
    private static final String PERSISTENCE_UNIT_NAME = "EconometricaPU";
    private static EntityManagerFactory emf = null;
    @PersistenceContext
    private static EntityManager em = null;
    
    //DRIVER Ο ΟΠΟΙΟΣ ΘΑ ΧΡΗΣΙΜΟΠΟΙΗΘΕΙ ΓΙΑ ΟΛΕΣ ΤΙΣ ΔΙΑΔΙΚΑΣΙΕΣ ΤΗΣ ΒΑΣΗΣ ΔΕΔΟΜΕΝΩΝ
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private static final String DB_URL = "jdbc:derby://localhost:1527/Econometrica_Data_DB"; 

    //ΔΙΑΠΙΣΤΕΥΤΗΡΙΑ ΒΑΣΗΣ ΔΕΔΟΜΕΝΩΝ
    private static final String USERNAME = "ge3_cpk";
    private static final String PASSWORD = "1234";

    
    
    
    public DbConnector() {
        try {
            //Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex); 
        }
    }

       
    //ΔΑΙΔΙΚΑΣΙΑ ΣΥΝΔΕΣΗΣ ΜΕ ΤΗΝ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ 
    public static Connection connect(){
        Connection conn = null;
        try {
            //ΔΗΜΙΟΥΡΓΙΑ ΣΥΝΔΕΣΗΣ ΣΤΗ  ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ ΜΕΣΩ ΤΟΥ DRIVER ΚΑΙ ΤΩΝ ΔΙΑΠΙΣΤΕΥΤΗΡΙΩΝ
            System.out.println("Πραγματοποιείται σύνδεση στη βάση δεδομένων...");
            //ΔΗΜΙΟΥΡΓΙΑ ΤΟΥ Entity Manager ΓΙΑ ΤΗ ΣΥΝΔΕΣΗ ΤΗΣ ΕΦΑΡΜΟΓΗΣ ΜΕ ΤΗΝ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ.
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            em = emf.createEntityManager();
            //ΚΑΤΑΧΩΡΗΣΗ ΣΤΗ ΜΕΤΑΒΛΗΤΗ conn ΤΥΠΟΥ Connection 
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Συνδέθηκε στη βάση δεδομένων.");    
            
        }catch(SQLException ex){
            System.out.println(ex); 
            //ΜΗΝΥΜΑ ΣΦΑΛΜΑΤΟΣ ΣΥΝΔΕΣΗΣ ΜΕ ΤΗΝ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ
            System.out.println("----------------------------------------------------------------------------");
              JOptionPane.showMessageDialog(null, "Αποτυχία σύνδεσης με τη Βάση Δεδομένων. ",
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }

    public static EntityManager getEm() {
        return em;
    }

}