package databaseClasses;

import databaseClasses.CountryDataset;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-03T20:55:23")
@StaticMetamodel(Country.class)
public class Country_ { 

    public static volatile CollectionAttribute<Country, CountryDataset> countryDatasetCollection;
    public static volatile SingularAttribute<Country, String> isoCode;
    public static volatile SingularAttribute<Country, String> name;

}