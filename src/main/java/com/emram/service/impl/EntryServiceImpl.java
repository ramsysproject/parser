package com.emram.service.impl;

import com.emram.config.HibernateFactory;
import com.emram.model.Entry;
import com.emram.service.EntryService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class EntryServiceImpl implements EntryService {

    @Autowired
    HibernateFactory hibernateFactory;

    @Override
    public Serializable create(Entry entry) {
        Session session = hibernateFactory.getSession();
        session.getTransaction().begin();
        Serializable id = session.save(entry);
        session.getTransaction().commit();
        session.close();

        return id;
    }
}
