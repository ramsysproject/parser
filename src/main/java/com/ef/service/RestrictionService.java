package com.ef.service;

import com.ef.model.Entry;
import com.ef.model.Restriction;

import java.io.Serializable;

public interface RestrictionService {
    Serializable create(Restriction restriction);
}
