package cz.muni.fi.pa165.hauntedhouses;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
	
public class HauntedhousesApplication {
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        // Start in-memory database
        new AnnotationConfigApplicationContext(PersistenceApplicationContext.class);
        
        emf = Persistence.createEntityManagerFactory("cz.muni.fi.pa165.hountedhouses");
        emf.close();
    }
}
