/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package econometrica;

import databaseClasses.*;
import org.jfree.ui.ApplicationFrame;
import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author ΧΡΗΣΤΟΣ ΜΠΑΡΜΠΑΣ - 084233
 * @author ΤΣΟΥΚΑΛΑΣ ΠΑΝΑΓΙΩΤΗΣ - 128374
 * @author ΧΑΤΖΗΚΥΡΙΑΚΙΔΟΥ ΚΥΡΙΑΚΗ - 100336
 */

public class PlotMaker extends ApplicationFrame{
    
    //ΛΙΣΤΕΣ ΓΙΑ ΔΙΑΧΩΡΙΣΜΟ ΤΩΝ ΑΝΤΙΚΕΙΜΕΝΩΝ ΤΩΝ 2 DATASETS
    private static List<CountryData> datalist1, datalist2; 
    static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
    final ChartPanel graphPanel;
    
    //CONSTRUCTOR
    public PlotMaker(String title, List<CountryData> alldata, List<CountryDataset> datasetList){
        
        super(title);
        final String chartTitle = title;
        
        datalist1 = new ArrayList<>();
        datalist2 = new ArrayList<>();
        
        //ΔΙΑΧΩΡΙΣΜΟΣ ΑΝΤΙΚΕΙΜΕΝΩΝ Country_Data ΓΙΑ ΤΟ ΚΑΘΕ DATASET ΑΠΟ ΤΗ ΛΙΣΤΑ 
        //alldata Η ΟΠΟΙΑ ΠΕΡΙΕΧΕΙ ΟΛΑ ΤΑ Country_Data ΤΗΣ ΧΩΡΑΣ
        for (int i = 0; i<alldata.size(); i++){           
            if(alldata.get(i).getDataset().getName().startsWith("Oil")){
                datalist1.add(alldata.get(i));
            }else{
                datalist2.add(alldata.get(i));
            }             
        }
        
        final XYDataset dataset = createDataset1();

        final JFreeChart graph = ChartFactory.createTimeSeriesChart(
                chartTitle,
                "Year",
                "GDP",
                dataset,
                true,
                true,
                false);

        final XYPlot plot = graph.getXYPlot();
        final NumberAxis axis2 = new NumberAxis("Oil Consumption");
        axis2.setAutoRangeIncludesZero(false);
        plot.setRangeAxis(1, axis2);
        plot.setDataset(1, createDataset2());
        plot.mapDatasetToRangeAxis(1, 1);
        final XYItemRenderer renderer = plot.getRenderer();
        renderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        
        if (renderer instanceof StandardXYItemRenderer) {
            final StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
            rr.setShapesFilled(true);
        }
        
        final StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
        renderer2.setSeriesPaint(0, Color.BLUE);
        renderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        plot.setRenderer(1, renderer2);
        
        final DateAxis axis = (DateAxis)plot.getDomainAxis();
        axis.setDateFormatOverride(dateformat);
        
        graphPanel = new ChartPanel(graph);
        graphPanel.setPreferredSize(new java.awt.Dimension(1500, 800));
        setContentPane(graphPanel);      

    }      
    
    
    //ΜΕΘΟΔΟΣ ΔΗΜΙΟΥΡΓΙΑΣ XYDataset
    private static XYDataset createDataset1(){
        
        Date year = null;
        Number value = null;
        final TimeSeries s1 = new TimeSeries("Gross Domestic Product (GDP)", Year.class);
        //NumberFormat ΓΙΑ ΑΝΤΙΜΕΤΩΠΙΣΗ ΤΟΥ ΚΟΜΜΑ ΣΤΟΝ ΑΡΙΘΜΟ
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        
        //ΓΙΑ ΚΑΘΕ ΑΝΤΙΚΕΙΜΕΝΟ Country_Data ΤΟΥ dataset1 ΧΡΗΣΙΜΟΠΟΙΟΥΜΕ ΤΙΜΗ ΚΑΙ ΕΤΟΣ
        for (int i = 0; i<datalist2.size(); i++){           
            try {
                value = nf.parse(datalist2.get(i).getValue());
                year = dateformat.parse(datalist2.get(i).getDataYear());
                
            } catch (ParseException ex) {
                Logger.getLogger(PlotMaker.class.getName()).log(Level.SEVERE, null, ex);
            }
            s1.add(new Year(year), value);
        }

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);

        return dataset;   
    }
    
    
    //ΜΕΘΟΔΟΣ ΔΗΜΙΟΥΡΓΙΑΣ XYDataset
    private XYDataset createDataset2() {
        
        Date year = null;
        Number value = null;
        final TimeSeries s2 = new TimeSeries("Oil Consumption", Year.class);
        //NumberFormat ΓΙΑ ΑΝΤΙΜΕΤΩΠΙΣΗ ΤΟΥ ΚΟΜΜΑ ΣΤΟΝ ΑΡΙΘΜΟ
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        
        for (int i = 0; i<datalist1.size(); i++){  
            try {
                value = nf.parse(datalist1.get(i).getValue());
                year = dateformat.parse(datalist1.get(i).getDataYear());
             
            } catch (ParseException ex) {
                Logger.getLogger(PlotMaker.class.getName()).log(Level.SEVERE, null, ex);
            }
            s2.add(new Year(year), value);
        }

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s2);

        return dataset; 
    }
    
}
