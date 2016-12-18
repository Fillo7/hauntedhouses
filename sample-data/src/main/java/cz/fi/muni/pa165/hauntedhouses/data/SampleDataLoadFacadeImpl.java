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
 * @author All team members
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
        House tower = createHouse("Xardas' Tower", "I don't even remember");
        House whiteHouse = createHouse("White house", "Washington DC");
        House oldCreeky = createHouse("Old creeky house", "Creepy avenue 14, Cakington");
        House aperture = createHouse("Aperture", "Aperture laboratories");

        // Load monsters
        Monster kitty = createMonster("Little Kitty", "little sweet kitty", LocalTime.of(10, 20), LocalTime.of(13, 40), tower);
        Monster huggy = createMonster("Huggy Bear", "Snoop Dogg Huggy Bear", LocalTime.of(4, 0), LocalTime.of(4, 5), tower);
        Monster grumpy = createMonster("Grumpy cat", "So grumpy it makes you cry. It doesn't even care.", LocalTime.of(5, 6), LocalTime.of(7, 3), oldCreeky);
        Monster glados = createMonster("GLaDOS", "Promises tasty cake", LocalTime.of(12,0), LocalTime.of(18, 0), aperture);

        // Load abilities
        Ability rainbows = createAbility("Spawn rainbows", "Spawns rainbows all around the place, blinding anyone who dares to wander nearby.", kitty);
        Ability cakePromise = createAbility("Cake lust", "Causes unresistible lust after tasty cakes", glados);
        Ability frog = createAbility("Spawn frogs", "Spawns thousands of frogs which do nothing" , huggy, grumpy);

        // Load cursed objects
        CursedObject rubbish = createCursedObject("Pile of rubbish", "Seemingly harmless", MonsterAttractionFactor.MEDIUM, tower);
        CursedObject theCoin = createCursedObject("The Coin", "Gain 1 mana crystal this turn only.", MonsterAttractionFactor.LOW, tower);
        CursedObject redButton = createCursedObject("Red button", "Kirov reporting", MonsterAttractionFactor.HIGH, whiteHouse);
        CursedObject cake = createCursedObject("Cake", "... is a lie", MonsterAttractionFactor.LOW, aperture);

        // Load users
        User regular = createUser("ming", "lee", UserRole.REGULAR_USER);
        User admin = createUser("admin", "opieop", UserRole.ADMIN);
    }

    private House createHouse(String name, String address) {
        House house = new House();

        house.setName(name);
        house.setAddress(address);

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
