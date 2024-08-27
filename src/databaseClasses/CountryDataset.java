/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClasses;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ΧΡΗΣΤΟΣ ΜΠΑΡΜΠΑΣ - 084233
 * @author ΤΣΟΥΚΑΛΑΣ ΠΑΝΑΓΙΩΤΗΣ - 128374
 * @author ΧΑΤΖΗΚΥΡΙΑΚΙΔΟΥ ΚΥΡΙΑΚΗ - 100336
 */

@Entity
@Table(name = "COUNTRY_DATASET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CountryDataset.findAll", query = "SELECT c FROM CountryDataset c")
    , @NamedQuery(name = "CountryDataset.findByStartYear", query = "SELECT c FROM CountryDataset c WHERE c.startYear = :startYear")
    , @NamedQuery(name = "CountryDataset.findByName", query = "SELECT c FROM CountryDataset c WHERE c.name = :name")
    , @NamedQuery(name = "CountryDataset.findByEndYear", query = "SELECT c FROM CountryDataset c WHERE c.endYear = :endYear")
    , @NamedQuery(name = "CountryDataset.findByDescription", query = "SELECT c FROM CountryDataset c WHERE c.description = :description")
    , @NamedQuery(name = "CountryDataset.findByDatasetId", query = "SELECT c FROM CountryDataset c WHERE c.datasetId = :datasetId")
    //NamedQuery ΓΙΑ ΕΥΡΕΣΗ Dataset ΜΕ ΒΑΣΗ ΤΟ countryCode
    , @NamedQuery(name = "CountryDataset.findByCountryCode", query = "SELECT c FROM CountryDataset c WHERE c.countryCode = :countryCode")
    //NamedQuery ΓΙΑ ΔΙΑΓΡΑΦΗ ΟΛΩΝ ΤΩΝ ΔΕΔΟΜΕΝΩΝ ΤΟΥ ΠΙΝΑΚΑ COUNTRY_DATASET
    , @NamedQuery(name = "CountryDataset.deleteAll", query = "DELETE FROM CountryDataset")}) 


public class CountryDataset implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "START_YEAR")
    private String startYear;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @Column(name = "END_YEAR")
    private String endYear;
    @Column(name = "DESCRIPTION")
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DATASET_ID")
    private Integer datasetId;
    @JoinColumn(name = "COUNTRY_CODE", referencedColumnName = "ISO_CODE")
    @ManyToOne
    private Country countryCode;
    @OneToMany(mappedBy = "dataset")
    private Collection<CountryData> countryDataCollection;

    public CountryDataset() {
    }

    public CountryDataset(Integer datasetId) {
        this.datasetId = datasetId;
    }

    public CountryDataset(Integer datasetId, String startYear, String name, String endYear) {
        this.datasetId = datasetId;
        this.startYear = startYear;
        this.name = name;
        this.endYear = endYear;
    }
    
    public CountryDataset(Integer datasetId, String name, String description, String startYear,  String endYear, Country countryCode) {
        this.datasetId = datasetId;
        this.name = name;
        this.description = description;
        this.startYear = startYear;
        this.endYear = endYear;
        this.countryCode = countryCode;
    }
    
    public CountryDataset(String name, String description, String startYear,  String endYear, Country countryCode) {
        
        this.name = name;
        this.description = description;
        this.startYear = startYear;
        this.endYear = endYear;
        this.countryCode = countryCode;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        String oldStartYear = this.startYear;
        this.startYear = startYear;
        changeSupport.firePropertyChange("startYear", oldStartYear, startYear);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", oldName, name);
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        String oldEndYear = this.endYear;
        this.endYear = endYear;
        changeSupport.firePropertyChange("endYear", oldEndYear, endYear);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        changeSupport.firePropertyChange("description", oldDescription, description);
    }

    public Integer getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Integer datasetId) {
        Integer oldDatasetId = this.datasetId;
        this.datasetId = datasetId;
        changeSupport.firePropertyChange("datasetId", oldDatasetId, datasetId);
    }

    public Country getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Country countryCode) {
        Country oldCountryCode = this.countryCode;
        this.countryCode = countryCode;
        changeSupport.firePropertyChange("countryCode", oldCountryCode, countryCode);
    }

    @XmlTransient
    public Collection<CountryData> getCountryDataCollection() {
        return countryDataCollection;
    }

    public void setCountryDataCollection(Collection<CountryData> countryDataCollection) {
        this.countryDataCollection = countryDataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datasetId != null ? datasetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CountryDataset)) {
            return false;
        }
        CountryDataset other = (CountryDataset) object;
        return !((this.datasetId == null && other.datasetId != null) || (this.datasetId != null && !this.datasetId.equals(other.datasetId)));
    }

    @Override
    public String toString() {
        return "databaseClasses.CountryDataset[ datasetId=" + datasetId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
