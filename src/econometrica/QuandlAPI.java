/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package econometrica;

import java.io.IOException;
import javax.swing.JOptionPane;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author ΧΡΗΣΤΟΣ ΜΠΑΡΜΠΑΣ - 084233
 * @author ΤΣΟΥΚΑΛΑΣ ΠΑΝΑΓΙΩΤΗΣ - 128374
 * @author ΧΑΤΖΗΚΥΡΙΑΚΙΔΟΥ ΚΥΡΙΑΚΗ - 100336
 */

public class QuandlAPI {
    private final String key = "xyRco6LY7qzLkh5M7_cy";
    private String urlToCall;
    private String isocode;
    private String responseString;

    public String getIsocode() {
        return isocode;
    }

    public void setIsocode(String isocode) {
        this.isocode = isocode;
    }

    public QuandlAPI(String isocode) {
        this.isocode = isocode;
    }

    public String GDP_quandlCall(){
        
        urlToCall = "https://www.quandl.com/api/v3/datasets/WWDI/" + isocode +
                "_NY_GDP_MKTP_CN.json?api_key=" + key;
        
        return QuandlCall(urlToCall);

    }
    
    public String OilConsumption_quandlCall(){
        
        urlToCall = "https://www.quandl.com/api/v3/datasets/BP/OIL_CONSUM_" + 
                isocode + ".json?api_key=" + key;

        return QuandlCall(urlToCall);
           
    }
    
    public String QuandlCall(String url){
        
        responseString = null;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                responseString = response.body().string();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Δεν είναι δυνατή η λήψη δεδομένων! Ελέγξτε τη σύνδεση με το internet.");
        }
        
        return responseString;
    }
}
