package com.ef.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateFactory {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
