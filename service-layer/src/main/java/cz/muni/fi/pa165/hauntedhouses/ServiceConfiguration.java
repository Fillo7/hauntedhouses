package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.exceptions.ServiceExceptionTranslateAspect;
import cz.muni.fi.pa165.hauntedhouses.facade.MonsterFacadeImpl;
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
@ComponentScan(basePackages = "cz.muni.fi.pa165.hauntedhouses")
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        return beanMapper;
    }

    /*@Bean
    public Mapper dozer() {
        List<String> mappingFiles = new ArrayList();
        mappingFiles.add("dozerJdk8Converters.xml");

        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.setMappingFiles(mappingFiles);
        return dozer;
    }*/
}
