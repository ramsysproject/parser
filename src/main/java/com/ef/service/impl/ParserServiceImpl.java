package com.ef.service.impl;

import com.ef.model.Entry;
import com.ef.service.EntryService;
import com.ef.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ParserServiceImpl implements ParserService {

    @Autowired
    EntryService entryService;

    @Override
    public Entry parseLine(String line) throws ParseException {
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

    @Override
    public Map findConsumingIps(String inputDate, String duration, Integer threshold) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.hh:mm:ss");
        Date startDate = new Date();
        try {
            startDate = format.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return entryService.findRequestsQuantityByIp(startDate, calculateEndingDate(startDate, duration));
    }

    private Date calculateEndingDate(Date startDate, String duration) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        if(duration.equals("hourly")) {
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            return calendar.getTime();
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            return calendar.getTime();
        }
    }
}
