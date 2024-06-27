public interface ToDoService {
    public String getStatus(Integer id);
    public void updateData(Integer id, String desc, String title, String status, int userId);
    public void deleteData(Integer id, int userId);
    public void readData(int userId);
    public void insertData(String desc, String title, int userId);
}
