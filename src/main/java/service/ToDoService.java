package service;

import entity.ToDo;
import entity.User;

import java.util.List;

public interface ToDoService {
    public List<User> checkStatus(String nameLogin, String pwLogin);
    public void insertUser(String nameUser, String pwUser);
    public long countUser();
    public ToDo findToDoById(int id);
    public void updateToDo(ToDo toDo);
    public void deleteData(Integer id, int userId);
    public List<ToDo> readData(int userId);
    public List<ToDo> checkToDo(int id, int userId);
    public void insertData(String desc, String title, int userId);
}
