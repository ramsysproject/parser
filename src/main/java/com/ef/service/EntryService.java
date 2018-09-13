package com.ef.service;

import com.ef.model.Entry;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public interface EntryService {
    Serializable create(Entry entry);
    Map<String, Long> findRequestsThresholdExceedingIpAddresses(Date startDate, Date endingDate, Long threshold);
}
