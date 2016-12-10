package cz.fi.muni.pa165.hauntedhouses.data;

import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.entity.User;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import cz.muni.fi.pa165.hauntedhouses.enums.UserRole;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.CursedObjectService;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.MonsterService;
import cz.muni.fi.pa165.hauntedhouses.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalTime;

/**
 * @author Ondrej Oravcok, Filip Petrovic
 */
@Transactional
@Component
public class SampleDataLoadFacadeImpl implements SampleDataLoadFacade {
    @Inject
    private HouseService houseService;
    
    @Inject
    private MonsterService monsterService;
    
    @Inject
    private AbilityService abilityService;
    
    @Inject
    private CursedObjectService cursedObjectService;
    
    @Inject
    private UserService userService;

    @Override
    @SuppressWarnings("unused")
    public void loadData() {
        // Load houses
        House house = createHouse("Spooky", "Some address");
        
        // Load monsters
        Monster kitty = createMonster("Little Kitty", "little sweet kitty", LocalTime.of(10, 20), LocalTime.of(13, 40), house);
        Monster huggy = createMonster("Huggy Bear", "Snoop Dogg Huggy Bear", LocalTime.of(4, 0), LocalTime.of(4, 5), house);
        
        // Load abilities
        Ability rainbows = createAbility("Spawn rainbows", "Spawns rainbows all around the place, blinding anyone who dares to wander nearby.", kitty);
        
        // Load cursed objects
        CursedObject object = createCursedObject("object", "some description", MonsterAttractionFactor.MEDIUM, house);
        
        // Load users
       User regular = createUser("regular", "regular", UserRole.REGULAR_USER);
       User admin = createUser("admin", "admin", UserRole.ADMIN);
    }
    
    private House createHouse(String name, String address) {
        House house = new House();

        house.setAddress(name);
        house.setName(address);

        houseService.create(house);
        return house;
    }
    
    private Monster createMonster(String name, String description, LocalTime hauntedStart, LocalTime hauntedEnd, House house) {
        Monster monster = new Monster();

        monster.setName(name);
        monster.setDescription(description);
        monster.setHauntedIntervalStart(hauntedStart);
        monster.setHauntedIntervalEnd(hauntedEnd);
        monster.setHouse(house);

        monsterService.create(monster);
        return monster;
    }
    
    private Ability createAbility(String name, String description, Monster... monsters) {
        Ability ability = new Ability();
        
        ability.setName(name);
        ability.setDescription(description);
        
        for (Monster monster : monsters) {
            ability.addMonster(monster);
        }
        
        abilityService.create(ability);
        return ability;
    }
    
    private CursedObject createCursedObject(String name, String description, MonsterAttractionFactor monsterAttractionFactor, House house) {
        CursedObject cursedObject = new CursedObject();
        
        cursedObject.setName(name);
        cursedObject.setDescription(description);
        cursedObject.setMonsterAttractionFactor(monsterAttractionFactor);
        cursedObject.setHouse(house);
        
        cursedObjectService.create(cursedObject);
        return cursedObject;
    }
    
    private User createUser(String login, String password, UserRole userRole) {
        User user = new User();
        
        user.setLogin(login);
        user.setUserRole(userRole);
        
        userService.create(user, password);
        return user;
    }
}
