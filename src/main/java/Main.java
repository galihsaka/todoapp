import com.enigma.InputValidation;
import entity.ToDo;
import entity.User;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InputValidation inputValidation = new InputValidation();
        Scanner scanner = new Scanner(System.in);
        ToDoServiceImpl service = new ToDoServiceImpl();
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
                    System.out.print("Masukkan username: ");
                    String nameLogin=scanner.nextLine();
                    System.out.print("Masukkan password: ");
                    String pwLogin=scanner.nextLine();
                    List<User> userRegistered = service.checkStatus(nameLogin, pwLogin);
                    if(userRegistered.size()<=0){
                        System.out.println("Username atau password salah!");
                    }
                    else {
                            System.out.println("Berhasil Login");
                            System.out.println("Selamat Datang "+userRegistered.get(0).getUsername()+"!");
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
                                            service.insertData(inputDesc, inputTitle, userRegistered.get(0).getId());
                                        } else if (chooseMenu.equals("2")) {
                                            System.out.println("----------------------------------");
                                            System.out.println("MENU 2 LIHAT AGENDA");
                                            System.out.println("----------------------------------");
                                            List<ToDo> todolist=service.readData(userRegistered.get(0).getId());
                                            if(todolist.size()<=0){
                                                System.out.println("Data Kosong!");
                                            }
                                            else {
                                                int display = 1;
                                                for (int i = 0; i < todolist.size(); i++) {
                                                    System.out.println("AGENDA " + display);
                                                    System.out.print("ID: ");
                                                    System.out.println(todolist.get(i).getId());
                                                    System.out.print("Judul: ");
                                                    System.out.println(todolist.get(i).getTitle());
                                                    System.out.print("Deskripsi: ");
                                                    System.out.println(todolist.get(i).getDescription());
                                                    System.out.print("Status: ");
                                                    System.out.println(todolist.get(i).getStatus());
                                                    display++;
                                                }
                                                display = 1;
                                            }
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
                                            List<ToDo> storeStatus = service.checkToDo(Integer.parseInt(idUpdate),userRegistered.get(0).getId());
                                            if (storeStatus.size()<=0) {
                                                System.out.println("Data yang ingin anda update tidak ditemukan!");
                                            } else {
                                                ToDo todoFound=service.findToDoById(Integer.parseInt(idUpdate));
                                                String stats = "";
                                                if (storeStatus.get(0).getStatus().equals("on work")) {
                                                    stats = "done";
                                                } else {
                                                    stats = "on work";
                                                }
                                                System.out.println("Status agenda akan diubah dari " + storeStatus.get(0).getStatus() + " ke " + stats);
                                                System.out.print("Masukkan judul baru: ");
                                                String updTitle = scanner.nextLine();
                                                System.out.print("Masukkan deskripsi baru: ");
                                                String updDesc = scanner.nextLine();
                                                todoFound.setDescription(updDesc);
                                                todoFound.setTitle(updTitle);
                                                todoFound.setStatus(stats);
                                                service.updateToDo(todoFound);
                                                //service.updateData(Integer.parseInt(idUpdate), updDesc, updTitle, stats, userRegistered.get(0).getId());
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
                                            List<ToDo> storeStatus = service.checkToDo(Integer.parseInt(inputDelete), userRegistered.get(0).getId());
                                            if (storeStatus.size()<=0) {
                                                System.out.println("Data yang ingin anda hapus tidak ditemukan!");
                                            } else {
                                                service.deleteData(Integer.parseInt(inputDelete), userRegistered.get(0).getId());
                                            }
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
                } else if (chooseLogin.equals("2")) {
                    System.out.print("Masukkan username: ");
                    String nameUser=scanner.nextLine();
                    System.out.print("Masukkan password: ");
                    String pwUser=scanner.nextLine();
                    service.insertUser(nameUser, pwUser);
                } else if (chooseLogin.equals("3")) {
                    long count=service.countUser();
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
