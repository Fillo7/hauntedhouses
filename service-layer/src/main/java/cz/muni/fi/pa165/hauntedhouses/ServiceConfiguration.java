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
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ondro on 09-Nov-16.
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan("cz.muni.fi.pa165.hauntedhouses")
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
