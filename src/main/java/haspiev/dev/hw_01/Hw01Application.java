package haspiev.dev.hw_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "haspiev.dev.hw_01")
@PropertySource("classpath:application.properties")
public class Hw01Application {

    public static void main(String[] args) {

//        SpringApplication.run(Hw01Application.class, args);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Получаем компонент OperationsConsoleListener
        OperationsConsoleListener listener = context.getBean(OperationsConsoleListener.class);

        // Запускаем консольный слушатель
        listener.startListening();

        // Закрываем контекст при завершении работы
        context.close();
    }

}
