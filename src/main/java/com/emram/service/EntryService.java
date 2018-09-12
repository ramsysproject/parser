package com.emram.service;

import com.emram.model.Entry;

import java.io.Serializable;

public interface EntryService {
    Serializable create(Entry entry);
}
