package com.emram.service.impl;

import com.emram.model.Entry;
import com.emram.service.EntryService;
import com.emram.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParserServiceImpl implements ParserService {

    @Autowired
    EntryService entryService;

    @Override
    public Entry parseLine(String line) {
        String[] parts = line.split("\\|");

        Entry entry = new Entry.Builder()
                .date(parts[0])
                .ipAddress(parts[1])
                .request(parts[2])
                .status(Integer.parseInt(parts[3]))
                .userAgent(parts[4])
                .build();

        entryService.create(entry);

        return entry;
    }
}
