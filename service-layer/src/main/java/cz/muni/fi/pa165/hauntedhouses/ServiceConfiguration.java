package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.exceptions.TranslateAspectException;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacadeImpl;
import cz.muni.fi.pa165.hauntedhouses.service.HouseServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * Created by Ondro on 09-Nov-16.
 */
@Configuration
//@EnableAspectJAutoProxy
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses = {HouseServiceImpl.class, HouseFacadeImpl.class, TranslateAspectException.class})
public class ServiceConfiguration {

}
