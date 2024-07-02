package service;

import entity.ToDo;
import entity.User;
import jakarta.persistence.*;
import util.JPAUtil;

import java.util.List;

public class ToDoServiceImpl implements ToDoService {
    EntityManager em = JPAUtil.getEMF();

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
    public ToDo findToDoById(int id){
        return em.find(ToDo.class,id);
    }

    public void updateToDo(ToDo toDo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(toDo);
        transaction.commit();
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

    public List<ToDo> checkToDo(int id, int userId){
        Query todo = em.createNativeQuery("SELECT * FROM to_do WHERE id= ? AND user_id= ?", ToDo.class);
        todo.setParameter(1, id);
        todo.setParameter(2, userId);
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
