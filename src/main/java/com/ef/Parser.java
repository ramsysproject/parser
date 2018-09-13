package com.ef;

import com.ef.constants.DurationEnum;
import com.ef.processor.ProcessorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.text.ParseException;

@ComponentScan({
        "com.ef.model",
        "com.ef.service",
        "com.ef.processor",
        "com.ef.config"
})
@SpringBootApplication
@EnableAutoConfiguration(exclude = {JpaRepositoriesAutoConfiguration.class})
public class Parser {

    private static ProcessorService processorService;
    private static final String ARGUMENTS_ERROR = "Please input inputStartDate, duration and threshold parameters";
    private static final String THRESHOLD_ERROR = "Threshold must be an Integer";
    private static final String THRESHOLD_GREATER_THAN_ERROR = "Threshold my be greater than 0";

    public static void main(String[] args) throws ParseException, IOException {
        ApplicationContext applicationContext = SpringApplication.run(Parser.class);

        if(args.length < 3) throw new IllegalArgumentException(ARGUMENTS_ERROR);
        String inputStartDate = args[0];
        String duration = args[1];
        String threshold = args[2];

        String validStartDate = validateStartDateArgument(inputStartDate);
        DurationEnum validDuration = validateDuration(duration);
        Long validThreshold = validateThreshold(threshold);

        processorService = applicationContext.getBean(ProcessorService.class);
        if(args.length == 3) processorService.processCpFile("access.log");
        else processorService.processDiskFile(args[3]);
        processorService.printConsumingIps(validStartDate, validDuration, validThreshold);
        System.exit(0);
    }

    private static String validateStartDateArgument(String inputStartDate) {
        return inputStartDate;
    }

    private static DurationEnum validateDuration(String duration) {
        return DurationEnum.findByName(duration);
    }

    private static Long validateThreshold(String inputThreshold) {
        Long threshold = 0L;
        try {
            threshold = Long.parseLong(inputThreshold);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(THRESHOLD_ERROR);
        }

        if(threshold <= 0) throw new IllegalArgumentException(THRESHOLD_GREATER_THAN_ERROR);

        return threshold;
    }
}
