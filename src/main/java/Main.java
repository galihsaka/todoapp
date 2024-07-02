import com.enigma.InputValidation;
import component.Menu;
import configuration.DIConfiguration;
import entity.ToDo;
import entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.ToDoService;
import service.ToDoServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(DIConfiguration.class);
        Menu menu=context.getBean(Menu.class);
        menu.start();
        context.close();
    }
}
