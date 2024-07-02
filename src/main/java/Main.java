import component.Menu;
import configuration.DIConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(DIConfiguration.class);
        Menu menu=context.getBean(Menu.class);
        menu.start();
        context.close();
    }
}
