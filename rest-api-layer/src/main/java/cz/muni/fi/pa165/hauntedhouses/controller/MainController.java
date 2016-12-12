package cz.muni.fi.pa165.hauntedhouses.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Filip Petrovic (422334)
 */
@RestController
public class MainController {
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {
        Map<String,String> resourcesMap = new HashMap<>();
        resourcesMap.put("houses_uri", "/houses");
        resourcesMap.put("monsters_uri", "/monsters");
        resourcesMap.put("cursed_objects_uri", "/cursedObjects");
        resourcesMap.put("abilities_uri", "/abilities");
        resourcesMap.put("users_uri", "/users");

        return Collections.unmodifiableMap(resourcesMap);
    }
}
