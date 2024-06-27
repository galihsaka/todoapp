import java.sql.*;
public class ToDoServiceImpl implements ToDoService {
    private ResultSet resultSet = null;
    private PreparedStatement preparedstatement = null;
    private Connection connection = null;
    private Integer display=1;
    public String getStatus(Integer id){
        String stat="";
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_todoapp", "postgres", "laptoplenovo");
            preparedstatement = connection.prepareStatement("SELECT status FROM to_do WHERE id=?");
            preparedstatement.setInt(1, id);
            resultSet = preparedstatement.executeQuery();
            while (resultSet.next()) {
                stat = resultSet.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (preparedstatement != null) {
                try {
                    preparedstatement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return stat;
    }
    public void updateData(Integer id, String desc, String title, String status, int userId){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_todoapp", "postgres", "laptoplenovo");
            preparedstatement = connection.prepareStatement("UPDATE to_do SET description=?, title=?, status=? WHERE id=? AND user_id=?");
            preparedstatement.setInt(4, id);
            preparedstatement.setInt(5, userId);
            preparedstatement.setString(1, desc);
            preparedstatement.setString(2, title);
            preparedstatement.setString(3, status);
            int successupdate=preparedstatement.executeUpdate();
            if(successupdate>0){
                System.out.println("Update data berhasil!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            if (preparedstatement != null) {
                try {
                    preparedstatement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public void deleteData(Integer id, int userId){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_todoapp", "postgres", "laptoplenovo");
            preparedstatement = connection.prepareStatement("DELETE FROM to_do WHERE id=? AND user_id=?");
            preparedstatement.setInt(1, id);
            preparedstatement.setInt(2, userId);
            int successdelete=preparedstatement.executeUpdate();
            if(successdelete>0){
                System.out.println("Delete data berhasil!");
            }
            else System.out.println("Data yang ingin anda hapus tidak ditemukan!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            if (preparedstatement != null) {
                try {
                    preparedstatement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public void readData(int userId){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_todoapp", "postgres", "laptoplenovo");
            preparedstatement = connection.prepareStatement("SELECT * FROM to_do WHERE user_id=?");
            preparedstatement.setInt(1, userId);
            resultSet = preparedstatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("AGENDA "+display);
                System.out.println("Id: " + resultSet.getInt(1));
                System.out.println("Judul Agenda: "+ resultSet.getString(4));
                System.out.println("Deskripsi: " + resultSet.getString(2));
                System.out.println("Status: " + resultSet.getString(3));
                System.out.println();
                display++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (preparedstatement != null) {
                try {
                    preparedstatement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if(display==1){
            System.out.println("Data Kosong!");
        }
        display=1;
    }
    public void insertData(String desc, String title, int userId){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_todoapp", "postgres", "laptoplenovo");
            preparedstatement = connection.prepareStatement("INSERT INTO to_do(description, status, title, user_id) VALUES(?, 'on work', ?, ?)");
            preparedstatement.setString(1, desc);
            preparedstatement.setString(2, title);
            preparedstatement.setInt(3, userId);
            int successinsert=preparedstatement.executeUpdate();
            if(successinsert>0){
                System.out.println("Insert data berhasil!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            if (preparedstatement != null) {
                try {
                    preparedstatement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
