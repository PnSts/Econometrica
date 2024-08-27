package databaseClasses;

import databaseClasses.CountryDataset;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-03T20:55:23")
@StaticMetamodel(CountryData.class)
public class CountryData_ { 

    public static volatile SingularAttribute<CountryData, String> dataYear;
    public static volatile SingularAttribute<CountryData, Integer> id;
    public static volatile SingularAttribute<CountryData, String> value;
    public static volatile SingularAttribute<CountryData, CountryDataset> dataset;

}