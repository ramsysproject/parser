package com.ef.processor.impl;

import com.ef.constants.DurationEnum;
import com.ef.model.Entry;
import com.ef.model.Restriction;
import com.ef.processor.ProcessorService;
import com.ef.service.EntryService;
import com.ef.service.RestrictionService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

@Component
public class ProcessorServiceImpl implements ProcessorService {

    private EntryService entryService;
    private RestrictionService restrictionService;

    private final String RESTRICTED_MSG = "Surpassed %s requests in %s scheme beginning at %s - Executed %s requests";
    private final String RESULTS_BEGIN = "\n\n############# Results #############";
    private final String RESULTS_END = "############# End Results #############\n\n";
    private final String RESULTS_HEADER = "ViolatingIpAddress ---- RequestsQuantity";

    @Autowired
    public ProcessorServiceImpl(EntryService entryService, RestrictionService restrictionService) {
        this.entryService = entryService;
        this.restrictionService = restrictionService;
    }

    @Override
    public Boolean processCpFile(String fileName) throws ParseException, IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        InputStream inputStream = classPathResource.getInputStream();
        File file = File.createTempFile("test", ".txt");
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

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

    public Boolean processDiskFile(String filePath) throws ParseException, IOException {
        File file = new File(filePath);

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
    public void informRestrictedIpAddresses(String inputStartDate, DurationEnum duration, Long threshold) {
        Map<String, Long> ipAddresses = findConsumingIps(inputStartDate, duration, threshold);
        System.out.println(RESULTS_BEGIN);
        System.out.println(RESULTS_HEADER);
        ipAddresses.forEach((String ip, Long quantity) -> {
            System.out.print(ip + " ---- "+ quantity + "\n");
            Restriction restriction = new Restriction.Builder().ipAddress(ip).motive(String.format(RESTRICTED_MSG, threshold, duration.getName(), inputStartDate, quantity)).build();
            restrictionService.create(restriction);
        });
        System.out.println(RESULTS_END);
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
