package com.ef.service.impl;

import com.ef.config.HibernateFactory;
import com.ef.model.Entry;
import com.ef.service.EntryService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

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

    @Override
    public Map<Integer, String> findRequestsQuantityByIp(Date startDate, Date endingDate) {
        return null;
    }
}
