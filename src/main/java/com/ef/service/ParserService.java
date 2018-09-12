package com.ef.service;

import com.ef.model.Entry;

import java.text.ParseException;
import java.util.Map;

public interface ParserService {
    Entry parseLine(String line) throws ParseException;
    Map findConsumingIps(String startDate, String duration, Integer threshold);
}
