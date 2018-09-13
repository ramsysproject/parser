package com.ef.service.impl;

import com.ef.config.HibernateFactory;
import com.ef.model.Entry;
import com.ef.service.EntryService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    public Map<String, Long> findRequestsThresholdExceedingIpAddresses(Date startDate, Date endingDate, Long threshold) {
        Session session = hibernateFactory.getSession();

        Map<String, Long> ipAddresses = new HashMap<>();
        List<Object[]> entryList = session.createQuery("SELECT e.ipAddress, COUNT(*) FROM Entry e WHERE e.date BETWEEN :startDate AND :endingDate GROUP BY e.ipAddress HAVING COUNT(*) > :threshold ORDER BY COUNT(*) ASC")
                .setParameter("startDate", startDate)
                .setParameter("endingDate", endingDate)
                .setParameter("threshold", threshold).list();

        for(Object[] eachObject: entryList) {
            ipAddresses.put(String.valueOf(eachObject[0]), Long.parseLong(eachObject[1].toString()));
        }

        return ipAddresses;
    }
}
