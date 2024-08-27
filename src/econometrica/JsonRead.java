/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package econometrica;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import databaseClasses.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ΧΡΗΣΤΟΣ ΜΠΑΡΜΠΑΣ - 084233
 * @author ΤΣΟΥΚΑΛΑΣ ΠΑΝΑΓΙΩΤΗΣ - 128374
 * @author ΧΑΤΖΗΚΥΡΙΑΚΙΔΟΥ ΚΥΡΙΑΚΗ - 100336
 */
public class JsonRead {
    
    CountryDataset cDataset;    //ΑΝΤΙΚΕΙΜΕΝΟ CountryDataset
    CountryData cData;          //ΑΝΤΙΚΕΙΜΕΝΟ CountryData
    List<CountryData> allData = new ArrayList<>();  //ΛΙΣΤΑ ΜΕ ΟΛΑ ΤΑ CountryData ΤΗΣ ΧΩΡΑΣ
    List<String> datasetInfo;   //ΛΙΣΤΑ ΜΕ ΠΛΗΡΟΦΟΡΙΕΣ ΤΟΥ dataset ΓΙΑ ΕΜΦΑΝΙΣΗ ΣΤΟ GUI

    
    public CountryDataset GetDataFromJson(String jsonString, Country country){
        
        datasetInfo = new ArrayList<>();
        
        //ΔΙΑΧΕΙΡΙΣΗ ΔΕΔΟΜΕΝΩΝ ΤΥΠΟΥ JSON
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(jsonString);

        if(jsonTree.isJsonObject()){

            JsonObject jsonObject = jsonTree.getAsJsonObject();

            JsonElement dataset = jsonObject.get("dataset");
            if(dataset.isJsonObject()){

                JsonObject datasetObj = dataset.getAsJsonObject();

                String name = datasetObj.get("name").getAsString();
                String description = datasetObj.get("description").getAsString();
                String startDate = datasetObj.get("start_date").getAsString();
                String endDate = datasetObj.get("end_date").getAsString();
                JsonElement data = datasetObj.get("data");                                
                    
                datasetInfo.add(name);
                datasetInfo.add(startDate);
                datasetInfo.add(endDate);

                //DATEFORMAT ΓΙΑ ΔΙΑΧΕΙΡΙΣΗ ΗΜΕΡΟΜΗΝΙΩΝ
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");

                String startYear, endYear;
                Date sDate = null, eDate = null;                                
                try {
                    sDate = dateformat.parse(startDate);
                    eDate = dateformat.parse(endDate);
                } catch (ParseException ex) {
                    Logger.getLogger(EconometricaGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                startYear = dateformat.format(sDate);
                endYear = dateformat.format(eDate);

                //ΔΗΜΙΟΥΡΓΙΑ ΛΙΣΤΑΣ ΑΝΤΙΚΕΙΜΕΝΩΝ CountryDataset ΜΕ ΣΚΟΠΟ ΤΗΝ ΑΠΟΘΗΚΕΥΣΗ ΣΤΗ ΒΑΣΗ
                cDataset = new CountryDataset(name, description, startYear, endYear, country);

                //ΔΙΑΧΕΙΡΙΣΗ ΔΕΔΟΜΕΝΩΝ ΤΥΠΟΥ JSON_ARRAY          
                if(data.isJsonArray()){

                    JsonArray dataArray = data.getAsJsonArray();
                    for(int i=0; i<dataArray.size(); i++){

                        JsonElement idata = dataArray.get(i);
                        if(idata.isJsonArray()){

                            JsonArray yearDataArray = idata.getAsJsonArray();

                            String date = yearDataArray.get(0).getAsString();
                            Double value = yearDataArray.get(1).getAsDouble();

                            Date yearDate = null;

                            //ΜΕΤΑΤΡΟΠΗ dataDate String ΣΕ Date                                             
                            try {
                                yearDate = dateformat.parse(date);
                            } catch (ParseException ex) {
                                Logger.getLogger(EconometricaGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //ΜΕΤΑΤΡΟΠΗ Date ΣΕ String ΜΕ ΔΙΑΤΗΡΗΣΗ ΜΟΝΟ ΤΟΥ ΕΤΟΥΣ
                            String year= dateformat.format(yearDate);
                            
                            //ΜΕΤΑΤΡΟΠΕΑΣ ΤΙΜΗΣ ΣΕ 2 ΔΕΚΑΔΙΚΑ
                            DecimalFormat df1 = new DecimalFormat("0.00");

                            cData = new CountryData(year, df1.format(value), cDataset);
                            allData.add(cData);
                        }
                    }                                                                      
                }
            }
        }    
    return cDataset;
    }
    
}
