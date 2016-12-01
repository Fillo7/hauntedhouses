package cz.fi.muni.pa165.hauntedhouses;

import cz.fi.muni.pa165.hauntedhouses.data.SampleDataLoadFacade;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by Ondrej Oravcok on 01-Dec-16.
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = SampleDataLoadFacade.class)
public class SampleDataConfiguration {

    @Inject
    private SampleDataLoadFacade sampleDataLoadFacade;

    @PostConstruct
    public void dataLoading() throws IOException{
        sampleDataLoadFacade.loadData();
    }

}
