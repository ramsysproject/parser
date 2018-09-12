package com.emram;

import com.emram.service.ParserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@ComponentScan({
        "com.emram.model",
        "com.emram.service",
        "com.emram.config"
})
@SpringBootApplication
@EnableAutoConfiguration(exclude = {JpaRepositoriesAutoConfiguration.class})
public class Application {

    private static ParserService parserService;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class);

        parserService = applicationContext.getBean(ParserService.class);
        Application application = new Application();
        application.processFile("access.log");
    }

    private String processFile(String fileName) {
        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
                parserService.parseLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
