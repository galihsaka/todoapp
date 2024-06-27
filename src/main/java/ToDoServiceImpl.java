import entity.ToDo;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class ToDoServiceImpl {
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("my-persistence");
    EntityManager em = emf.createEntityManager();

    public List<User> checkStatus(String nameLogin, String pwLogin){
        Query users = em.createNativeQuery("SELECT * FROM m_user WHERE username = ? AND password = ?", User.class);
        users.setParameter(1, nameLogin);
        users.setParameter(2, pwLogin);
        return users.getResultList();
    }
    public void insertUser(String nameUser, String pwUser){
        User user1=new User();
        user1.setUsername(nameUser);
        user1.setPassword(pwUser);
        em.getTransaction().begin();
        em.persist(user1);
        System.out.println("Tambah data user berhasil");
        em.getTransaction().commit();
    }
    public long countUser(){
        Query users = em.createNativeQuery("SELECT * FROM m_user", User.class);
        List<User> userList = users.getResultList();
        return userList.stream().count();
    }
    public void updateData(Integer id, String desc, String title, String status, int userId){
        Query todo = em.createNativeQuery("UPDATE to_do SET description=?, title=?, status=? WHERE id=? AND user_id=?", ToDo.class);
        todo.setParameter(1, desc);
        todo.setParameter(2, title);
        todo.setParameter(3, status);
        todo.setParameter(4, id);
        todo.setParameter(5, userId);
        em.getTransaction().begin();
        todo.executeUpdate();
        System.out.println("Update Agenda Berhasil!");
        em.getTransaction().commit();
    }
    public void deleteData(Integer id, int userId){
        Query todo = em.createNativeQuery("DELETE FROM to_do WHERE id=? AND user_id=?", ToDo.class);
        todo.setParameter(1, id);
        todo.setParameter(2, userId);
        em.getTransaction().begin();
        todo.executeUpdate();
        System.out.println("Delete Agenda Berhasil!");
        em.getTransaction().commit();
    }
    public List<ToDo> readData(int userId){
        Query todo = em.createNativeQuery("SELECT * FROM to_do WHERE user_id= ?", ToDo.class);
        todo.setParameter(1, userId);
        return todo.getResultList();
    }

    public List<ToDo> checkToDo(int id){
        Query todo = em.createNativeQuery("SELECT * FROM to_do WHERE id= ?", ToDo.class);
        todo.setParameter(1, id);
        return todo.getResultList();
    }
    public void insertData(String desc, String title, int userId){
        Query todo = em.createNativeQuery("INSERT INTO to_do(description, status, title, user_id) VALUES(?, 'on work', ?, ?)", ToDo.class);
        todo.setParameter(1, desc);
        todo.setParameter(2, title);
        todo.setParameter(3, userId);
        em.getTransaction().begin();
        todo.executeUpdate();
        System.out.println("Tambah Agenda Berhasil!");
        em.getTransaction().commit();
    }

}
