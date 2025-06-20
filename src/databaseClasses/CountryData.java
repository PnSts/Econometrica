/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClasses;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ΧΡΗΣΤΟΣ ΜΠΑΡΜΠΑΣ - 084233
 * @author ΤΣΟΥΚΑΛΑΣ ΠΑΝΑΓΙΩΤΗΣ - 128374
 * @author ΧΑΤΖΗΚΥΡΙΑΚΙΔΟΥ ΚΥΡΙΑΚΗ - 100336
 */

@Entity
@Table(name = "COUNTRY_DATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CountryData.findAll", query = "SELECT c FROM CountryData c")
    , @NamedQuery(name = "CountryData.findById", query = "SELECT c FROM CountryData c WHERE c.id = :id")
    , @NamedQuery(name = "CountryData.findByDataYear", query = "SELECT c FROM CountryData c WHERE c.dataYear = :dataYear")
    , @NamedQuery(name = "CountryData.findByValue", query = "SELECT c FROM CountryData c WHERE c.value = :value")
    //NamedQuery ΓΙΑ ΕΥΡΕΣΗ Data ΜΕ ΒΑΣΗ ΤΟ dataset
    , @NamedQuery(name = "CountryData.findByDataset", query = "SELECT c FROM CountryData c WHERE c.dataset  = :dataset ORDER BY c.dataYear DESC")
    //NamedQuery ΓΙΑ ΔΙΑΓΡΑΦΗ ΟΛΩΝ ΤΩΝ ΔΕΔΟΜΕΝΩΝ ΤΟΥ ΠΙΝΑΚΑ COUNTRY_DATA
    , @NamedQuery(name = "CountryData.deleteAll", query = "DELETE FROM CountryData")}) 

public class CountryData implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DATA_YEAR")
    private String dataYear;
    @Basic(optional = false)
    @Column(name = "VALUE")
    private String value;
    @JoinColumn(name = "DATASET", referencedColumnName = "DATASET_ID")
    @ManyToOne
    private CountryDataset dataset;

    public CountryData() {
    }

    public CountryData(Integer id) {
        this.id = id;
    }

    public CountryData(Integer id, String dataYear, String value) {
        this.id = id;
        this.dataYear = dataYear;
        this.value = value;
    }

    public CountryData(Integer id, String dataYear, String value, CountryDataset dataset) {
        this.id = id;
        this.dataYear = dataYear;
        this.value = value;
        this.dataset = dataset;
    }
    
    public CountryData(String dataYear, String value, CountryDataset dataset) {
        this.dataYear = dataYear;
        this.value = value;
        this.dataset = dataset;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getDataYear() {
        return dataYear;
    }

    public void setDataYear(String dataYear) {
        String oldDataYear = this.dataYear;
        this.dataYear = dataYear;
        changeSupport.firePropertyChange("dataYear", oldDataYear, dataYear);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        String oldValue = this.value;
        this.value = value;
        changeSupport.firePropertyChange("value", oldValue, value);
    }

    public CountryDataset getDataset() {
        return dataset;
    }

    public void setDataset(CountryDataset dataset) {
        CountryDataset oldDataset = this.dataset;
        this.dataset = dataset;
        changeSupport.firePropertyChange("dataset", oldDataset, dataset);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CountryData)) {
            return false;
        }
        CountryData other = (CountryData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "databaseClasses.CountryData[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
