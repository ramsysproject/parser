package com.ef.processor;

import com.ef.constants.DurationEnum;

import java.io.IOException;
import java.text.ParseException;

public interface ProcessorService {
    Boolean processCpFile(String fileName) throws ParseException, IOException;
    Boolean processDiskFile(String fileName) throws ParseException, IOException;
    void informRestrictedIpAddresses(String inputStartDate, DurationEnum duration, Long threshold);
}
