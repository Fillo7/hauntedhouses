package cz.muni.fi.pa165.hauntedhouses;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
	
public class HauntedhousesApplication {
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        // To do
        
        // Start in-memory database
        new AnnotationConfigApplicationContext(PersistenceApplicationContext.class);
        
        emf = Persistence.createEntityManagerFactory("cz.muni.fi.pa165.hountedhouses_hauntedhouses_jar_1.0-SNAPSHOTPU");
    }
}