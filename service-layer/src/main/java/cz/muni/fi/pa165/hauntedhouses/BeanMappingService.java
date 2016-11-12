package cz.muni.fi.pa165.hauntedhouses;

import java.util.Collection;
import java.util.List;
import org.dozer.Mapper;

/**
 * @author Filip Petrovic (422334)
 */
public interface BeanMappingService {
    Mapper getMapper();
    <T> T mapTo(Object object, Class<T> mapToClass);
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);
}
