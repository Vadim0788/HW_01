package haspiev.dev.hw_01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan(basePackages = "haspiev.dev.hw_01")
@PropertySource("classpath:application.properties")
public class Hw01Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OperationsConsoleListener listener = context.getBean(OperationsConsoleListener.class);
        listener.startListening();
        context.close();
    }


}
