package cz.fi.muni.pa165.hauntedhouses.data;

import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
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

    private final Map<String, Monster> monsters = new HashMap<>();

    @Inject
    private MonsterService monsterService;

    @Override
    public void loadData() {
        generateMonsters();
        generateHouses();
    }

    private void generateMonsters(){
        monsters.put("kitty", createMonster("Little Kitty", "little sweet kitty", LocalTime.of(10, 20), LocalTime.of(13, 40)));
        monsters.put("huggy", createMonster("Huggy Bear", "Snoop Dogg Huggy Bear", LocalTime.of(4, 0), LocalTime.of(4, 5)));
    }

    private void generateHouses(){

    }

    private Monster createMonster(String name, String description, LocalTime hauntedStart, LocalTime hauntedEnd){
        Monster monster = new Monster();

        monster.setName(name);
        monster.setDescription(description);
        monster.setHauntedIntervalStart(hauntedStart);
        monster.setHauntedIntervalEnd(hauntedEnd);

        monsterService.create(monster);
        return monster;
    }

}
