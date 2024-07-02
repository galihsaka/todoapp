package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import service.ToDoService;
import service.ToDoServiceImpl;
@Configuration
@ComponentScan(value={"component"})
public class DIConfiguration {
    @Bean
    public ToDoService getToDoService(){
    return new ToDoServiceImpl();
    }
}
