package databaseClasses;

import databaseClasses.Country;
import databaseClasses.CountryData;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-03T20:55:22")
@StaticMetamodel(CountryDataset.class)
public class CountryDataset_ { 

    public static volatile SingularAttribute<CountryDataset, Country> countryCode;
    public static volatile SingularAttribute<CountryDataset, String> startYear;
    public static volatile SingularAttribute<CountryDataset, String> name;
    public static volatile SingularAttribute<CountryDataset, String> description;
    public static volatile SingularAttribute<CountryDataset, Integer> datasetId;
    public static volatile SingularAttribute<CountryDataset, String> endYear;
    public static volatile CollectionAttribute<CountryDataset, CountryData> countryDataCollection;

}