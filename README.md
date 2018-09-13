# parser
**Web Server Access Log Parser**

This application reads a Web Server logs, parses and persists entries into a Database. At this moment it´s working with an H2 Database for dev purposes and MySQL connector for Prod.

The application by default runs in port 8090. There are several ways to execute it:


1) ```java -cp parser-1.0.jar -Dloader.main=com.ef.Parser org.springframework.boot.loader.PropertiesLauncher {startDateParam} {durationParam} {thresholdParam} {pathToFileParam - Optional}```

Example:
```java -cp parser-1.0.jar -Dloader.main=com.ef.Parser org.springframework.boot.loader.PropertiesLauncher 2017-01-01.00:00:00 daily 500```

**_Note: When the pathToFile param isnt´t provided, the application will run parsing and access log that resides in the classpath._**

2) ```java -jar parser-1.0.jar {startDateParam} {durationParam} {thresholdParam} {pathToFileParam - Optional}```

Example:
```java -jar parser-1.0.jar 2017-01-01.00:00:00 daily 500```

3) If running from an IDE, you can set the ```spring.profiles.active=dev``` property to inject Hibernate configuration for using an in memory H2 Database. The h2 console can be accessed by the url ```http://localhost:8090/h2```

4) If running from an IDE without setting a profile, it´ll inject Hibernate Configuration for a MySQL database (make sure the schema named "parser" is created)

**_Note: The ddl method is create-drop. This means the tables and data are generated when the application starts, but also dropped when it´s stopped._**

**Technical Details**
- Spring Boot Application
- Hibernate ORM
- H2 DB for Dev and MySQL DB for Prod
- Gradle Dependency Management and Build
