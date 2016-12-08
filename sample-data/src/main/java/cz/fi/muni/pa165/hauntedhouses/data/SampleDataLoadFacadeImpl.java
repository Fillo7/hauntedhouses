package cz.fi.muni.pa165.hauntedhouses.data;

import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.MonsterService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * implementation of SampleDataLoad Facade
 *
 * Created by Ondrej Oravcok on 01-Dec-16.
 */
@Transactional
@Component
public class SampleDataLoadFacadeImpl implements SampleDataLoadFacade {
    
    private final Map<String, House> houses = new HashMap<>();
    private final Map<String, Monster> monsters = new HashMap<>();
    
    @Inject
    private HouseService houseService;
    
    @Inject
    private MonsterService monsterService;

    @Override
    public void loadData() {
        generateHouses();
        generateMonsters();
    }

    private void generateHouses() {
        houses.put("house", createHouse("Spooky", "Some address"));
    }
    
    private void generateMonsters() {
        monsters.put("kitty", createMonster("Little Kitty", "little sweet kitty", LocalTime.of(10, 20), LocalTime.of(13, 40), houses.get("house")));
        monsters.put("huggy", createMonster("Huggy Bear", "Snoop Dogg Huggy Bear", LocalTime.of(4, 0), LocalTime.of(4, 5), houses.get("house")));
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
}
