package cz.muni.fi.pa165.hauntedhouses.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cz.muni.fi.pa165.hauntedhouses.configuration.Uri;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Filip Petrovic (422334)
 */
@RestController
public class MainController {
    @RequestMapping(value = "/rest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {
        Map<String,String> resourcesMap = new HashMap<>();
        resourcesMap.put("houses_uri", Uri.HOUSES);
        resourcesMap.put("monsters_uri", Uri.MONSTERS);
        resourcesMap.put("cursed_objects_uri", Uri.CURSED_OBJECTS);
        resourcesMap.put("abilities_uri", Uri.ABILITIES);
        resourcesMap.put("users_uri", Uri.USERS);

        return Collections.unmodifiableMap(resourcesMap);
    }
}
