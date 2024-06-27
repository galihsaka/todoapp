import com.enigma.InputValidation;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("my-persistence");
        EntityManager em = emf.createEntityManager();
        InputValidation inputValidation = new InputValidation();
        Scanner scanner = new Scanner(System.in);
        ToDoService service = new ToDoServiceImpl();
        String chooseMenu, inputDesc, inputTitle, inputDelete, idUpdate, chooseLogin;
        do{
            System.out.println("MAIN MENU");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Lihat Jumlah Akun Yang Terdaftar");
            System.out.println("4. Keluar");
        do{
            do {
                chooseLogin = scanner.nextLine();
                if (chooseLogin.equals("1")) {
                    System.out.println("Login");
                    System.out.print("Masukkan id: ");
                    int idLogin=scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Masukkan username: ");
                    String nameLogin=scanner.nextLine();
                    System.out.print("Masukkan password: ");
                    String pwLogin=scanner.nextLine();
                    User userFound = em.find(User.class, idLogin);
                    if(userFound==null){
                        System.out.println("Id tidak ditemukan!");
                    }
                    else {
                        if(userFound.getUsername().equals(nameLogin)&&userFound.getPassword().equals(pwLogin)){
                            System.out.println("Berhasil Login");
                            System.out.println("Selamat Datang "+userFound.getUsername()+"!");
                            do {
                                System.out.println("Pilih Menu");
                                System.out.println("1. Tambah Agenda");
                                System.out.println("2. Lihat Semua Agenda");
                                System.out.println("3. Update Agenda");
                                System.out.println("4. Hapus Agenda");
                                System.out.println("5. Log Out");
                                do {
                                    do {
                                        chooseMenu = scanner.nextLine();
                                        if (chooseMenu.equals("1")) {
                                            System.out.println("----------------------------------");
                                            System.out.println("MENU 1 TAMBAH AGENDA");
                                            System.out.println("----------------------------------");
                                            System.out.print("Masukkan judul agenda: ");
                                            inputTitle = scanner.nextLine();
                                            System.out.print("Masukkan deskripsi agenda: ");
                                            inputDesc = scanner.nextLine();
                                            service.insertData(inputDesc, inputTitle, userFound.getId());
                                        } else if (chooseMenu.equals("2")) {
                                            System.out.println("----------------------------------");
                                            System.out.println("MENU 2 LIHAT AGENDA");
                                            System.out.println("----------------------------------");
                                            service.readData(userFound.getId());
                                        } else if (chooseMenu.equals("3")) {
                                            System.out.println("----------------------------------");
                                            System.out.println("MENU 3 UPDATE AGENDA");
                                            System.out.println("----------------------------------");
                                            do {
                                                System.out.println("Masukkan id agenda yang ingin diupdate: ");
                                                idUpdate = scanner.nextLine();
                                                if (!inputValidation.isNumeric(idUpdate)) {
                                                    System.out.println("ID Agenda harus berupa angka!");
                                                }
                                            } while (!inputValidation.isNumeric(idUpdate));
                                            String storeStatus = service.getStatus(Integer.parseInt(idUpdate));
                                            if (storeStatus.isBlank()) {
                                                System.out.println("Data yang ingin anda update tidak ditemukan!");
                                            } else {
                                                String stats = "";
                                                if (storeStatus.equals("on work")) {
                                                    stats = "done";
                                                } else {
                                                    stats = "on work";
                                                }
                                                System.out.println("Status agenda akan diubah dari " + storeStatus + " ke " + stats);
                                                System.out.print("Masukkan judul baru: ");
                                                String updTitle = scanner.nextLine();
                                                System.out.print("Masukkan deskripsi baru: ");
                                                String updDesc = scanner.nextLine();
                                                service.updateData(Integer.parseInt(idUpdate), updDesc, updTitle, stats, userFound.getId());
                                            }
                                        } else if (chooseMenu.equals("4")) {
                                            System.out.println("----------------------------------");
                                            System.out.println("MENU 4 HAPUS AGENDA");
                                            System.out.println("----------------------------------");
                                            do {
                                                System.out.print("Masukkan id agenda yang ingin dihapus: ");
                                                inputDelete = scanner.nextLine();
                                                if (!inputValidation.isNumeric(inputDelete)) {
                                                    System.out.println("ID Agenda harus berupa angka!");
                                                }
                                            } while (!inputValidation.isNumeric(inputDelete));
                                            service.deleteData(Integer.parseInt(inputDelete), userFound.getId());
                                        } else if (chooseMenu.equals("5")) {
                                            System.out.println("Anda telah log out");
                                        } else {
                                            System.out.println("Menu tidak ada di pilihan");
                                        }
                                        if (!inputValidation.isNumeric(chooseMenu)) {
                                            System.out.println("Inputan terdeteksi karakter, mohon input ulang! (harus berupa angka antara 1-5): ");
                                        }
                                    } while (!inputValidation.isNumeric(chooseMenu));
                                    if (Integer.parseInt(chooseMenu) <= 0 || Integer.parseInt(chooseMenu) > 5) {
                                        System.out.println("Mohon input ulang! (harus berupa angka antara 1-5): ");
                                    }
                                } while (Integer.parseInt(chooseMenu) <= 0 || Integer.parseInt(chooseMenu) > 5);
                            }while(Integer.parseInt(chooseMenu)>0&&Integer.parseInt(chooseMenu)<5);
                        }
                        else System.out.println("Username atau password salah!");
                    }
                } else if (chooseLogin.equals("2")) {
                    System.out.println("Register");
                    System.out.print("Masukkan id: ");
                    int idUser=scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Masukkan username: ");
                    String nameUser=scanner.nextLine();
                    System.out.print("Masukkan password: ");
                    String pwUser=scanner.nextLine();
                    User user1=new User(idUser, pwUser,nameUser);
                    em.getTransaction().begin();
                    em.persist(user1);
                    em.getTransaction().commit();
                    System.out.println("Tambah data user berhasil");
                } else if (chooseLogin.equals("3")) {
                    Query users = em.createNativeQuery("SELECT * FROM m_user", User.class);
                    List<User> userList = users.getResultList();
                    long count=userList.stream().count();
                    System.out.print("Jumlah Akun Saat Ini: "+count);
                    System.out.println();
                } else if (chooseLogin.equals("4")) {
                        System.out.println("Anda Keluar Program");
                } else System.out.println("Menu tidak ada di pilihan");
                if (!inputValidation.isNumeric(chooseLogin)) {
                    System.out.println("Inputan terdeteksi karakter, mohon input ulang! (harus berupa angka antara 1-4): ");
                }
            }while(!inputValidation.isNumeric(chooseLogin));
            if (Integer.parseInt(chooseLogin) <= 0 || Integer.parseInt(chooseLogin) > 4) {
                System.out.println("Mohon input ulang! (harus berupa angka antara 1-4): ");
            }
            }while (Integer.parseInt(chooseLogin) <= 0 || Integer.parseInt(chooseLogin) > 4);
        }while (Integer.parseInt(chooseLogin)>0&&Integer.parseInt(chooseLogin)<4);
    }
}
