package com.ef.processor.impl;

import com.ef.constants.DurationEnum;
import com.ef.model.Entry;
import com.ef.processor.ProcessorService;
import com.ef.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

@Component
public class ProcessorServiceImpl implements ProcessorService {

    @Autowired
    EntryService entryService;

    @Override
    public Boolean processFile(String fileName) throws ParseException {
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parseLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File cannot be processed");
        }

        return Boolean.TRUE;
    }

    @Override
    public void printConsumingIps(String inputStartDate, DurationEnum duration, Long threshold) {
        Map<String, Long> ipAddresses = findConsumingIps(inputStartDate, duration, threshold);
        ipAddresses.forEach((String ip, Long quantity) -> System.out.print("ViolatingIpAddress: "+ ip + " - RequestsQuantity: "+ quantity + "\n"));
    }

    private Entry parseLine(String line) throws ParseException {
        String[] parts = line.split("\\|");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        Entry entry = new Entry.Builder()
                .date(format.parse(parts[0]))
                .ipAddress(parts[1])
                .request(parts[2])
                .status(Integer.parseInt(parts[3]))
                .userAgent(parts[4])
                .build();

        entryService.create(entry);

        return entry;
    }

    private Map findConsumingIps(String inputDate, DurationEnum duration, Long threshold) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.hh:mm:ss");
        Date startDate = new Date();
        try {
            startDate = format.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return entryService.findRequestsThresholdExceedingIpAddresses(startDate, calculateEndingDate(startDate, duration), threshold);
    }

    private Date calculateEndingDate(Date startDate, DurationEnum duration) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        switch (duration) {
            case HOURLY:
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                return calendar.getTime();
            case DAILY:
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                return calendar.getTime();
        }

        throw new IllegalArgumentException();
    }
}