package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.api.dto.AbilityDTO;
import cz.muni.fi.pa165.api.dto.CursedObjectDTO;
import cz.muni.fi.pa165.api.dto.HouseDTO;
import cz.muni.fi.pa165.api.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import cz.muni.fi.pa165.hauntedhouses.exceptions.ServiceExceptionTranslateAspect;
import cz.muni.fi.pa165.hauntedhouses.facade.CursedObjectFacadeImpl;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacadeImpl;
import cz.muni.fi.pa165.hauntedhouses.facade.MonsterFacadeImpl;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.service.CursedObjectServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.service.HouseServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.service.MonsterServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ondro on 09-Nov-16.
 */
@Configuration
@EnableAspectJAutoProxy
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses = {MonsterServiceImpl.class, HouseServiceImpl.class, CursedObjectServiceImpl.class, AbilityServiceImpl.class,
        MonsterFacadeImpl.class, HouseFacadeImpl.class, CursedObjectFacadeImpl.class, AbilityServiceImpl.class, ServiceExceptionTranslateAspect.class, BeanMappingServiceImpl.class})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer() {
        List<String> customMappingFiles = new ArrayList<>();
        customMappingFiles.add("dozerJdk8Converters.xml");
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        beanMapper.setMappingFiles(customMappingFiles);
        return beanMapper;
    }

}
