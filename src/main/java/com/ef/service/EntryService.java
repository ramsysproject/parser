package com.ef.service;

import com.ef.model.Entry;

import java.io.Serializable;

public interface EntryService {
    Serializable create(Entry entry);
}
