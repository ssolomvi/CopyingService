package ru.mai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.mai.config.property.ExecutorConfiguration;
import ru.mai.config.property.TimeToPrintConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(value = {TimeToPrintConfiguration.class, ExecutorConfiguration.class})
public class PrintApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrintApplication.class, args);
    }

}