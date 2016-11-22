package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.*;

/**
 * Created by Ondro on 09-Nov-16.
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165.hauntedhouses")
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
            mapping(House.class, HouseDTO.class);
            mapping(Ability.class, AbilityDTO.class);
            mapping(Monster.class, MonsterDTO.class);
        }
    }
}
