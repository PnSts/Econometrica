/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package econometrica;

import databaseClasses.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;


/**
 *
 * @author ΧΡΗΣΤΟΣ ΜΠΑΡΜΠΑΣ - 084233
 * @author ΤΣΟΥΚΑΛΑΣ ΠΑΝΑΓΙΩΤΗΣ - 128374
 * @author ΧΑΤΖΗΚΥΡΙΑΚΙΔΟΥ ΚΥΡΙΑΚΗ - 100336
 */

public class ControllerGUI {
    
    
    private static EntityManager em;
    
    private final String[] countries = new String[240];
    private final String[] countriesAlpha3Codes = new String[240];
    
    //CONSTRUCTOR
    public ControllerGUI(){
       
        //ΕΛΕΓΧΟΣ ΑΝ ΔΕΝ ΕΧΕΙ ΓΙΝΕΙ ΣΥΝΔΕΣΗ ΣΤΗ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ
        //ΚΑΙ ΚΛΗΣΗ ΤΗΣ ΚΛΑΣΗΣ ΣΥΝΔΕΣΗΣ ΣΤΗ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ
        if (em == null){           
            //ΚΛΗΣΗ FINAL CLASS DbConnector ΓΙΑ ΣΥΝΔΕΣΗ ΣΤΗ ΒΑΣΗ
            //ΚΑΙ ΔΗΜΙΟΥΡΓΙΑ entity factory ΚΑΙ entity manager
            DbConnector.connect();
            em = DbConnector.getEm();          
        }
        
        //ΔΙΑΒΑΣΜΑ ΔΙΑΘΕΣΙΜΩΝ ΧΩΡΩΝ ΚΑΙ alpha3 isocode ΑΠΟ ΑΡΧΕΙΟ .csv
        try {
            File file = new File("iso-countries.csv");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String lineCountry;
            String[] attributeCountry; 
            int i = 0;
            while ((lineCountry = bufferedReader.readLine()) != null) {
                attributeCountry = lineCountry.split(";"); 
                //ΚΑΤΑΧΩΡΙΣΗ ΟΝΟΜΑΤΩΝ ΧΩΡΩΝ ΣΕ ΠΙΝΑΚΑ
                if(!attributeCountry[0].equals("Name")){
                    countries[i] = attributeCountry[0];
                }else{
                    countries[i] = "Choose a country";
                }
                //ΚΑΤΑΧΩΡΙΣΗ alpha3 isocodes ΧΩΡΩΝ ΣΕ ΠΙΝΑΚΑ 
                countriesAlpha3Codes[i] = attributeCountry[2];
                i++;
            } 
        } 
        catch (IOException e) {
        }       
    }
    
    
    public String[] getCountries() {
        return countries;
    }

    public String[] getCountriesAlpha3Codes() {
        return countriesAlpha3Codes;
    }

    
    //ΜΕΘΟΔΟΣ ΕΥΡΕΣΗΣ isocode ΣΥΓΚΕΚΡΙΜΕΝΗΣ ΧΩΡΑΣ
    public String getIsocodeOfCountry(String countryName){
        int i;
        for (i = 0; i<countries.length; i++){
            if(countryName.equals(countries[i])){
                break;
            }
        }
        return countriesAlpha3Codes[i];
    }

    
    //ΜΕΘΟΔΟΣ ΑΝΑΖΗΤΗΣΗΣ ΚΑΤΑΧΩΡΗΜΕΝΗΣ ΕΓΓΡΑΦΗΣ
    public boolean FindInDbByIsocode(String isocode){
        
        Query q = em.createNamedQuery("Country.findByIsoCode", Country.class);
        q.setParameter("isoCode", isocode);                      
        
        //ΕΠΙΣΤΡΕΦΕΙ false ΑΝ ΔΕΝ ΥΠΑΡΧΕΙ ΚΑΤΑΧΩΡΙΣΗ ΚΑΙ true ΑΝ ΥΠΑΡΧΕΙ 
        return !q.getResultList().isEmpty();    
    }
    
    
    //ΜΕΘΟΔΟΣ ΑΝΑΖΗΤΗΣΗΣ CountryDataset ΜΕ ΒΑΣΗ ΤΟ isocode
    public List<CountryDataset> FindCountryDatasets(Country c){
        try {
            Query q = em.createNamedQuery("CountryDataset.findByCountryCode", CountryDataset.class);
            q.setParameter("countryCode", c);
            
            return (List<CountryDataset>) q.getResultList();
        }
        catch (Exception ex){
            return null;
        }     
    }
    
    
    //ΜΕΘΟΔΟΣ ΑΝΑΖΗΤΗΣΗΣ CountryData ΜΕ ΒΑΣΗ ΤΟ Dataset id
    public List<CountryData> FindCountryData(CountryDataset cd){
        try {
            Query q = em.createNamedQuery("CountryData.findByDataset", CountryData.class);
            q.setParameter("dataset", cd);
            
            return (List<CountryData>) q.getResultList();
        }
        catch (Exception ex){
            return null;
        }     
    }
    
    
    //ΜΕΘΟΔΟΣ ΑΠΟΘΗΚΕΥΣΗΣ ΔΕΔΟΜΕΝΩΝ ΣΤΟΝ ΠΙΝΑΚΑ Country
    public void insertDataToCountry(Country c){
        try{
            Query q = em.createNamedQuery("Country.findAll");

            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Προκλήθηκε σφάλμα κατά την εισαγωγή δεδομένων στη βάση. \n ",
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }       
    }
    
    
    //ΜΕΘΟΔΟΣ ΑΠΟΘΗΚΕΥΣΗΣ ΔΕΔΟΜΕΝΩΝ ΣΤΟΝ ΠΙΝΑΚΑ Country_Dataset
    public void insertDatasetsToCountryDataset(List<CountryDataset> cds){
        try{
            Query q = em.createNamedQuery("CountryDataset.findAll");


            em.getTransaction().begin();
            //ΕΠΑΝΑΛΗΠΤΙΚΗ ΔΙΑΔΙΚΑΣΙΑ ΓΙΑ ΚΑΤΑΧΩΡΗΣΗ ΤΗΣ ΛΙΣΤΑΣ cds
            cds.forEach((dataset) -> {
                //ΚΑΤΑΧΩΡΗΣΗ ΣΤΗ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ
                em.persist(dataset);
            });  
            //ΑΠΟΘΗΚΕΥΣΗ ΤΗΣ ΚΑΤΑΧΩΡΗΣΗΣ
            em.getTransaction().commit(); 
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Προκλήθηκε σφάλμα κατά την εισαγωγή δεδομένων στη βάση. \n ",
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
     
    //ΜΕΘΟΔΟΣ ΑΠΟΘΗΚΕΥΣΗΣ ΔΕΔΟΜΕΝΩΝ ΣΤΟΝ ΠΙΝΑΚΑ Country_Data
    public void insertDataToCountryData(List<CountryData> cd){
        try{
            Query q = em.createNamedQuery("CountryData.findAll");

            em.getTransaction().begin();
            //ΕΠΑΝΑΛΗΠΤΙΚΗ ΔΙΑΔΙΚΑΣΙΑ ΓΙΑ ΚΑΤΑΧΩΡΗΣΗ ΤΗΣ ΛΙΣΤΑΣ cd
            cd.forEach((data) -> {
                //ΚΑΤΑΧΩΡΗΣΗ ΣΤΗ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ
                em.persist(data);
            });  
            //ΑΠΟΘΗΚΕΥΣΗ ΤΗΣ ΚΑΤΑΧΩΡΗΣΗΣ
            em.getTransaction().commit(); 
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Προκλήθηκε σφάλμα κατά την εισαγωγή δεδομένων στη βάση. \n ",
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);           
        }      
    }
   
    //ΜΕΘΟΔΟΣ ΔΙΑΓΡΑΦΗΣ ΟΛΩΝ ΤΩΝ ΔΕΔΟΜΕΝΩΝ ΤΗΣ ΒΑΣΗΣ ΜΕ ΤΗΡΗΣΗ ΤΗΣ ΣΕΙΡΑ ΑΝΑΦΟΡΑΣ ΚΛΕΙΔΙΩΝ 
    public void clearTables() {
        
        try{ 
            Query q1 = em.createNamedQuery("CountryData.deleteAll");
            Query q2 = em.createNamedQuery("CountryDataset.deleteAll");
            Query q3 = em.createNamedQuery("Country.deleteAll");
            
            //ΔΙΑΓΡΑΦΗ ΔΕΔΟΜΕΝΩΝ ΠΙΝΑΚΑ COUNTRY_DATA
            em.getTransaction().begin();           
            q1.executeUpdate();
            em.getTransaction().commit();
            
            //ΔΙΑΓΡΑΦΗ ΔΕΔΟΜΕΝΩΝ ΠΙΝΑΚΑ COUNTRY_DATASET
            em.getTransaction().begin();  
            q2.executeUpdate();
            em.getTransaction().commit();
            
            //ΔΙΑΓΡΑΦΗ ΔΕΔΟΜΕΝΩΝ ΓΙΑ ΠΙΝΑΚΑ COUNTRY
            em.getTransaction().begin();  
            q3.executeUpdate();
            em.getTransaction().commit();
        }
        catch (Exception e){ 
            //ΣΕ ΠΕΡΙΠΤΩΣΗ ΣΦΑΛΜΑΤΟΣ ΕΠΑΝΑΦΟΡΑ
            em.getTransaction().rollback();
        }         
    }   

    
    //ΜΕΘΟΔΟΣ ΕΛΕΓΧΟΥ ΑΔΕΙΑΣ ΒΑΣΗΣ
    public boolean EmptyDB(){
        boolean empty = false;
        try {
            Query q = em.createNamedQuery("Country.findAll", Country.class);
            empty = q.getResultList().isEmpty();
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Προκλήθηκε σφάλμα κατά την αναζήτηση δεδομένων από τη βάση. \n ",
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }     
        return empty;
    }
    
    
}
