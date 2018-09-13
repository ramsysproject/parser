package com.ef.service.impl;

import com.ef.config.HibernateFactory;
import com.ef.model.Restriction;
import com.ef.service.RestrictionService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class RestrictionServiceImpl implements RestrictionService {

    @Autowired
    HibernateFactory hibernateFactory;

    @Override
    public Serializable create(Restriction restriction) {
        Session session = hibernateFactory.getSession();
        session.getTransaction().begin();
        Serializable id = session.save(restriction);
        session.getTransaction().commit();
        session.close();

        return id;
    }
}
