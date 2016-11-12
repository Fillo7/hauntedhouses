package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.api.dto.CursedObjectDTO;
import cz.muni.fi.pa165.api.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Ondro on 09-Nov-16.
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan("cz.muni.fi.pa165.hauntedhouses")
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        DozerBeanMapper beanMapper = new DozerBeanMapper();		
        beanMapper.addMapping(new DozerMappingConfiguration());
        return beanMapper;
    }
    
    public class DozerMappingConfiguration extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(CursedObject.class, CursedObjectDTO.class);
            mapping(cz.muni.fi.pa165.api.enums.MonsterAttractionFactor.class, MonsterAttractionFactor.class);
            mapping(House.class, HouseDTO.class);
            // To do: Add rest of the needed mappings as they are introduced during project implementation.
        }
    }
}
