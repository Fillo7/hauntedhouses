package cz.muni.fi.pa165.hauntedhouses;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ondro on 09-Nov-16.
 */
@Configuration
@EnableAspectJAutoProxy
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165.hauntedhouses")
public class ServiceConfiguration {

    @Bean
    public Mapper dozer() {
        List<String> mappingFiles = new ArrayList();
        mappingFiles.add("dozerJdk8Converters.xml");

        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.setMappingFiles(mappingFiles);
        return dozer;
    }
}
