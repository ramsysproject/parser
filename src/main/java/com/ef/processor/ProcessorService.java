package com.ef.processor;

import com.ef.constants.DurationEnum;

import java.text.ParseException;

public interface ProcessorService {
    Boolean processFile(String fileName) throws ParseException;
    void printConsumingIps(String inputStartDate, DurationEnum duration, Long threshold);
}
