#developed by Dmitry Mikhalko

#application capabilities are described in the searcher_test.pdf file in the same package. 
#instructions for launching the application are presented below.

1. Сheck the **JRE version** on the system - not less than **version 11.**    
2. The **Maven version 2.4.2** or later.  
3. Database is **PostgreSQL 42.2.18** or later version. You must also **create a database named filmsDB**
(or another name, but don't forget to change it in application.properties too).  

4. Find the database property file in path: **movie-searcher/src/main/resources/application.properties.**  
4.1 Change the value of parameter **spring.datasource.url** for your url (there you must verify database name from p3).     
4.2 Change values of parameters **spring.datasource.username** and **spring.datasource.password** to your own,
be careful - these values have to be equals with **spring.flyway.user** and **spring.flyway.password parameters.**  
4.3 You can change values for spring.jpa.properties.hibernate.default_schema and spring.flyway.schemas - the same value for both.  

5. In command line enter **mvn clean install** and wait a few time until you see "BUILD SUCCESS" in comand line.
You'll get a new package **target** in current directory which contain file movie-searcher-1.0.jar  
6. In command line enter **java -jar target/movie-searcher-1.0.jar** and wait while application is running. Final line
will be **Started MovieSearcherApplication in XX second**  
