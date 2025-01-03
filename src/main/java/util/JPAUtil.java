package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

public class JPAUtil {
    private static EntityManagerFactory emf=null;
    public static EntityManager getEMF(){
        Map<String, String> properties=Map.of(
                "jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/db_todoapp",
                "jakarta.persistence.jdbc.user", "postgres",
                "jakarta.persistence.jdbc.password", ""
        );
        emf= Persistence.createEntityManagerFactory("my-persistence", properties);
        return emf.createEntityManager();
    }
    public static void shutdown(){
        if(emf!=null){
            emf.close();
        }
    }
}
