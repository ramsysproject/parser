package com.ef.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConf {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    private final String PACKAGE_MODEL = "com.ef.model";
    private final String DDL_AUTO_KEY = "hibernate.hbm2ddl.auto";
    private final String DIALECT_KEY = "hibernate.dialect";
    private final String CREATE_DROP_VALUE = "create-drop";
    private final String DIALECT_MYSQL_VALUE = "org.hibernate.dialect.MySQL5Dialect";
    private final String DIALECT_H2_VALUE = "org.hibernate.dialect.H2Dialect";

    @Bean
    @Primary
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setPackagesToScan(PACKAGE_MODEL);
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setHibernateProperties(hibernateProperties());

        return sessionFactoryBean;
    }

    @Bean
    @Profile({"dev"})
    public LocalSessionFactoryBean sessionFactoryBeanDev() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setPackagesToScan(PACKAGE_MODEL);
        sessionFactoryBean.setDataSource(dataSourceDev());
        sessionFactoryBean.setHibernateProperties(hibernatePropertiesDev());

        return sessionFactoryBean;
    }

    private DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder.create()
                .url(url)
                .driverClassName(driverClassName)
                .username(username)
                .password(password)
                .build();

        return dataSource;
    }

    private DataSource dataSourceDev() {
        DataSource dataSource = DataSourceBuilder.create()
                .url(url)
                .driverClassName(driverClassName)
                .username(username)
                .password(password)
                .build();

        return dataSource;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty(DDL_AUTO_KEY, CREATE_DROP_VALUE);
        hibernateProperties.setProperty(DIALECT_KEY, DIALECT_MYSQL_VALUE);

        return hibernateProperties;
    }

    private final Properties hibernatePropertiesDev() {
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty(DDL_AUTO_KEY, CREATE_DROP_VALUE);
        hibernateProperties.setProperty(DIALECT_KEY, DIALECT_H2_VALUE);

        return hibernateProperties;
    }
}
