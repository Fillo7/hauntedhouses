package cz.muni.fi.pa165.hauntedhouses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

/**
 * @author Filip Petrovic (422334)
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {
    @Inject
    private Mapper mapper;
    
    @Override
    public Mapper getMapper() {
    	return mapper;
    }
    
    @Override
    public <T> T mapTo(Object object, Class<T> mapToClass) {
        return mapper.map(object, mapToClass);
    }
    
    @Override
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        
        for (Object object : objects) {
            mappedCollection.add(mapper.map(object, mapToClass));
        }
        return mappedCollection;
    }
}
