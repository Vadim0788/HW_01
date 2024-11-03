package haspiev.dev.hw_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Hw01Application {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(Hw01Application.class, args);

	}

}
