package cz.muni.fi.pa165.hauntedhouses;

import java.util.*;
import javax.inject.Inject;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

/**
 * @author Filip Petrovic (422334)
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {
    @Inject
    private Mapper dozer;

    @Override
    public Mapper getMapper() {
        return dozer;
    }
    
    @Override
    public <T> T mapTo(Object object, Class<T> mapToClass) {
        return (object == null) ? null : dozer.map(object, mapToClass);
    }
    
    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> Map<T, Integer> mapTo(Map<?, Integer> objects, Class<T> mapToClass) {
        Map<T, Integer> mappedCollection = new HashMap<>();
        for (Map.Entry<?, Integer> entry : objects.entrySet()) {
            mappedCollection.put(dozer.map(entry.getKey(), mapToClass), entry.getValue());
        }
        return mappedCollection;
    }
}
