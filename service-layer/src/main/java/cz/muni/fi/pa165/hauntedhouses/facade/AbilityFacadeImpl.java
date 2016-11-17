package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.api.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.api.dto.AbilityDTO;
import cz.muni.fi.pa165.api.facade.AbilityFacade;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kristyna Loukotova
 * @version 17.11.2016
 */
@Service
@Transactional
public class AbilityFacadeImpl implements AbilityFacade {
    @Inject
    private AbilityService abilityService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createAbility(AbilityCreateDTO abilityCreateDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateAbility(AbilityDTO abilityDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeAbility(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbilityDTO getAbilityById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AbilityDTO> getAllAbilities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
